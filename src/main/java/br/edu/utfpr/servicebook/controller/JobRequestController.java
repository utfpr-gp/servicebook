package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalSearchItemDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.DateUtil;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("/requisicoes")
@SessionAttributes("wizard")
public class JobRequestController {

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobAvailableToHideService jobAvailableToHideService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    JobRequestMapper jobRequestMapper;

    @Autowired
    ExpertiseMapper expertiseMapper;

    @Autowired
    private WizardSessionUtil<JobRequestDTO> wizardSessionUtil;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private IAuthentication authentication;

    @Value("${X_RAPIDAPI_KEY}")
    private String rapidapiKey;

    @Value("${X_RAPIDAPI_HOST}")
    private String rapidapiHots;

    public enum RequestDateSelect{
        today(0), tomorrow(1) , thisweek(2), nextweek(3), thismonth(4), nextmonth(5);

        private int value;

        RequestDateSelect(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }

    }

    @GetMapping
    @PermitAll
    public String showWizard(@RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
                             HttpSession httpSession,
                             Model model) {
        log.debug("Mostrando o passo {}", step);
        if(step < 1 || step > 8){
            step = 1L;
        }
        JobRequestDTO dto = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        model.addAttribute("dto", dto);

        if(step == 1L){
            List<Expertise> expertise = expertiseService.findAll();
            List<ExpertiseDTO> expertiseDTOs = expertise.stream()
                    .map(u -> expertiseMapper.toDto(u))
                    .collect(Collectors.toList());
            model.addAttribute("expertiseDTOs", expertiseDTOs);
        }

        return "client/job-request/wizard-step-0" + step;
    }

    @PostMapping("/passo-1")
    @PermitAll
    public String saveFormRequestedJob(HttpSession httpSession, @Validated(JobRequestDTO.RequestExpertiseGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model){
        Optional<Expertise> oExpertise = null;

        if(dto.getExpertiseId() != null) {
            oExpertise = expertiseService.findById(dto.getExpertiseId());
            if (!oExpertise.isPresent()) {
                errors.rejectValue("expertiseId","error.dto","Especialidade não encontrada! Por favor, selecione uma especialidade profissional.");
            }
        }

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            List<Expertise> expertises = expertiseService.findAll();
            List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                    .map(u -> expertiseMapper.toDto(u))
                    .collect(Collectors.toList());

            model.addAttribute("expertiseDTOs", expertiseDTOs);

            return "client/job-request/wizard-step-01";
        }

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setExpertiseId(dto.getExpertiseId());

        log.debug("Passo 1 {}", sessionDTO);

        return "redirect:/requisicoes?passo=2";
    }

    @PostMapping("/passo-2")
    @PermitAll
    public String saveFormDateJob(HttpSession httpSession, @Validated(JobRequestDTO.RequestExpirationGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model){


        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 2 {}", dto);
            log.debug("Errors 2 {}", errors);
            return "client/job-request/wizard-step-02";

        }
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);


        if(dto.getDateProximity() == RequestDateSelect.today.value){
            //Hoje
            log.debug("HOJE: {}", sessionDTO);
            sessionDTO.setDateTarget(DateUtil.getToday());
        }
        if(dto.getDateProximity() == RequestDateSelect.tomorrow.value){
            //Amanhã
            log.debug("Amanhã: {}", sessionDTO);
            sessionDTO.setDateTarget(DateUtil.getTomorrow());
        }
        if(dto.getDateProximity() == RequestDateSelect.thisweek.value){
            //Esta Semana
            log.debug("Esta Semana: {}", sessionDTO);
            sessionDTO.setDateTarget(DateUtil.getThisWeek());
        }
        if(dto.getDateProximity() == RequestDateSelect.nextweek.value){
            //Proxima Semana
            log.debug("Proxima Semana: {}", sessionDTO);
            sessionDTO.setDateTarget(DateUtil.getNextWeek());
        }
        if(dto.getDateProximity() == RequestDateSelect.thismonth.value){
            //Este Mês
            log.debug("Este Mês: {}", sessionDTO);
            sessionDTO.setDateTarget(DateUtil.getThisMonth());
        }
        if(dto.getDateProximity() == RequestDateSelect.nextmonth.value){
            //Proximo Mes
            log.debug("Proximo Mês: {}", sessionDTO);
            sessionDTO.setDateTarget(DateUtil.getNextMonth());
        }
        sessionDTO.setDateCreated(DateUtil.getToday());
        log.debug("Passo 2 {}", sessionDTO);
        return "redirect:/requisicoes?passo=3";

    }
    @PostMapping("/passo-3")
    @PermitAll
    public String saveFormMaxCandidates(HttpSession httpSession, @Validated(JobRequestDTO.RequestMaxCandidatesGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model){
        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 3 {}", dto);
            log.debug("Errors 3 {}", errors);
            return "client/job-request/wizard-step-03";

        }
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setQuantityCandidatorsMax(dto.getQuantityCandidatorsMax());

        log.debug("Passo 3 {}", sessionDTO);
        return "redirect:/requisicoes?passo=4";

    }


    @PostMapping("/passo-4")
    @PermitAll
    public String saveFormDescription(HttpSession httpSession, @Validated(JobRequestDTO.RequestDescriptionGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model){

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 4 {}", dto);
            log.debug("Errors 4 {}", errors);
            return "client/job-request/wizard-step-04";

        }

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setDescription(dto.getDescription());

        log.debug("Passo 4 {}", sessionDTO);

        return "redirect:/requisicoes?passo=5";

    }

    @PostMapping("/passo-5")
    @PermitAll
    public String saveFormImagePath(HttpSession httpSession, RedirectAttributes redirectAttributes, JobRequestDTO dto, Model model) throws IOException {


        //persiste na sessão
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setImageFile(dto.getImageFile());

        System.out.println(sessionDTO.getExpertiseId());
        System.out.println("entrei no passo 5");
        System.out.println(dto.getImageFile());

        if(isValidateImage(dto.getImageFile())){
            File jobImage = Files.createTempFile("temp", dto.getImageFile().getOriginalFilename()).toFile();
            dto.getImageFile().transferTo(jobImage);
            Map data = cloudinary.uploader().upload(jobImage, ObjectUtils.asMap("folder", "jobs"));



            //fAZER A VALIDAÇÃO AQUI
            if(nsfwFilter((String)data.get("url"))){
                String publicId = (String) data.get("public_id");
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                redirectAttributes.addFlashAttribute("msg", "A imagem enviada contém conteúdo impróprio. Por favor, envie outra foto.");
                return "redirect:/requisicoes?passo=5";
            }

            sessionDTO.setImageSession((String)data.get("url"));


            log.debug("Passo 5 {}", sessionDTO);

            return "redirect:/requisicoes/passo=6";
        } else {
            return "redirect:/requisicoes/passo=6";
        }
    }

    @GetMapping("passo=6")
    @PermitAll
    protected ModelAndView showProfessionals(HttpSession httpSession) {
        ModelAndView mv = new ModelAndView("client/job-request/wizard-step-06");

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);

        Optional<Expertise> expertise = expertiseService.findById(sessionDTO.getExpertiseId());

        List<Individual> professionals = individualService.findDistinctByTermIgnoreCase(expertise.get().getName());

        List<ProfessionalSearchItemDTO> professionalSearchItemDTOS = professionals.stream().limit(3)
                .map(s -> individualMapper.toSearchItemDto(s, individualService.getExpertises(s)))
                .collect(Collectors.toList());

        mv.addObject("professionals", professionalSearchItemDTOS);
        mv.addObject("professionalsAmount", professionalSearchItemDTOS.size());

        return mv;
    }



    @PostMapping("/passo-7")
    @PermitAll
    public String saveFormVerification(HttpSession httpSession, JobRequestDTO dto, RedirectAttributes redirectAttributes, Model model,SessionStatus status){

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);

        Optional<Expertise> oExpertise = expertiseService.findById(sessionDTO.getExpertiseId());

        if(!oExpertise.isPresent()){
            throw new InvalidParamsException("A especilidade informada não foi encontrada!");
        }

        Expertise exp = oExpertise.get();
        sessionDTO.setClientConfirmation(true);
        sessionDTO.setDateCreated(DateUtil.getToday());
        sessionDTO.setStatus("Requerido");

        log.debug("Passo 7 {}", sessionDTO);
        JobRequest jobRequest = jobRequestMapper.toEntity(sessionDTO);
        jobRequest.setExpertise(exp);

        //jobRequest.setImage(sessionDTO.getImageSession());
        jobRequestService.save(jobRequest);
        redirectAttributes.addFlashAttribute("msg", "Requisição confirmada!");
        status.setComplete();
        return "redirect:/requisicoes?passo=8";
    }

    @PostMapping("/passo-8")
    @PermitAll
    public String formConfirmation(HttpSession httpSession, BindingResult errors,JobRequestDTO dto, RedirectAttributes redirectAttributes, Model model,SessionStatus status){
        return "redirect:/requisicoes";
    }

    /**
     * Apresenta a página de sucesso ou falha, quando o usuário preenche uma requisição de serviço como visitante e só
     * depois que realizao login na aplicação.
     * Esta requisição de serviço é pega da sessão e então, é salva no BD e o usuário é encaminhado para este endereço.
     * TODO apresentar uma mensagem de erro genérica no JSP caso error seja true
     * @param isError informa se houve falha na persistência ou validação da requisição de serviço.
     * @param httpSession
     * @return
     */
    @GetMapping("pedido-recebido")
    @RolesAllowed({RoleType.USER})
    protected ModelAndView showMessageSuccessful(@RequestParam(value = "erro", required = false, defaultValue = "false") boolean isError, HttpSession httpSession) {
        System.out.println("Parâmetro erro: " + isError);
        ModelAndView mv = new ModelAndView("client/job-requested");

        Optional<Individual> individual = individualService.findByEmail(authentication.getEmail());

        mv.addObject("client", individual.get().getName());

        //apaga a sessão
        httpSession.invalidate();

        return mv;
    }

    public boolean isValidateImage(MultipartFile image){
        List<String> contentTypes = Arrays.asList("image/png", "image/jpg", "image/jpeg");

        for(int i = 0; i < contentTypes.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(contentTypes.get(i))){
                return true;
            }
        }

        return false;
    }

    /**
     * O profissional solicita para não ver mais o JobRequest informado na lista de disponíveis, não tendo interesse.
     * A persistência é temporária na tabela, pois ao passar de uns dias será removido por um job assíncrono.
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/nao-quero/{id}")
    @RolesAllowed({RoleType.USER})
    public String rememberToHide(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Salvo com sucesso!");
        String currentUserEmail = authentication.getEmail();

        Optional<User> oUser = userService.findByEmail(currentUserEmail);
        if(!oUser.isPresent()){
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobRequest> oJobRequest = jobRequestService.findById(id);
        if(!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("Candidatura não encontrada!");
        }

        JobAvailableToHide jobAvailableToHide = new JobAvailableToHide(oJobRequest.get(), oUser.get());
        this.jobAvailableToHideService.save(jobAvailableToHide);

        return "redirect:/minha-conta/profissional#disponiveis";
    }

    public boolean nsfwFilter(String imageUrl){
        RestTemplate restTemplate = new RestTemplate();

        // Define o URL da API que será chamada
        String apiUrl = "https://nsfw-image-classification1.p.rapidapi.com/img/nsfw";

        // Define o cabeçalho da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Rapidapi-Key", rapidapiKey);
        headers.set("X-Rapidapi-Host", rapidapiHots);

        // Define o corpo da requisição
        String requestBody = "{\"url\": \"" + imageUrl + "\"}";

        // Cria a requisição POST com os parâmetros do cabeçalho e corpo
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Faz a requisição POST para a API
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Verifica o código de status da resposta
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            String subtexto = responseBody.substring(14, 18);
            // Processa a resposta da API conforme necessário
            float numeroFloat = Float.parseFloat(subtexto);

            System.out.println("Resposta da API: " + subtexto);
            if(numeroFloat > 0.5){
                return true;
            }
            else{
                return false;
            }

        } else {
            System.out.println("A requisição falhou com o código de status: " + response.getStatusCode());
        }
        return true;
    }
}