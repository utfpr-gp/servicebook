package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.Client;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.service.ClientService;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.DateUtil;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private ClientService clientService;

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
    private UserService userService;


    public enum RequestDateSelect {
        today(0), tomorrow(1), thisweek(2), nextweek(3), thismonth(4), nextmonth(5);

        private int value;

        RequestDateSelect(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    @GetMapping
    public String showWizard(@RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
                             HttpSession httpSession,
                             Model model) {
        log.debug("Mostrando o passo {}", step);
        if (step < 1 || step > 8) {
            step = 1L;
        }
        JobRequestDTO dto = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        model.addAttribute("dto", dto);

        if (step == 1L) {
            List<Expertise> expertise = expertiseService.findAll();
            List<ExpertiseDTO> expertiseDTOs = expertise.stream()
                    .map(u -> expertiseMapper.toDto(u))
                    .collect(Collectors.toList());
            model.addAttribute("expertiseDTOs", expertiseDTOs);
        }

        return "client/job-request/wizard-step-0" + step;
    }

    @PostMapping("/passo-1")
    public String saveFormRequestedJob(HttpSession httpSession, @Validated(JobRequestDTO.RequestExpertiseGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
        Optional<Expertise> oExpertise = null;

        if (dto.getExpertiseId() != null) {
            oExpertise = expertiseService.findById(dto.getExpertiseId());
            if (!oExpertise.isPresent()) {
                errors.rejectValue("expertiseId", "error.dto", "Especialidade não encontrada! Por favor, selecione uma especialidade profissional.");
            }
        }

        if (errors.hasErrors()) {
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
    public String saveFormDateJob(HttpSession httpSession, @Validated(JobRequestDTO.RequestExpirationGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {


        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 2 {}", dto);
            log.debug("Errors 2 {}", errors);
            return "client/job-request/wizard-step-02";

        }
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);


        if (dto.getDateProximity() == RequestDateSelect.today.value) {
            //Hoje
            log.debug("HOJE: {}", sessionDTO);
            sessionDTO.setDateExpired(DateUtil.getToday());
        }
        if (dto.getDateProximity() == RequestDateSelect.tomorrow.value) {
            //Amanhã
            log.debug("Amanhã: {}", sessionDTO);
            sessionDTO.setDateExpired(DateUtil.getTomorrow());
        }
        if (dto.getDateProximity() == RequestDateSelect.thisweek.value) {
            //Esta Semana
            log.debug("Esta Semana: {}", sessionDTO);
            sessionDTO.setDateExpired(DateUtil.getThisWeek());
        }
        if (dto.getDateProximity() == RequestDateSelect.nextweek.value) {
            //Proxima Semana
            log.debug("Proxima Semana: {}", sessionDTO);
            sessionDTO.setDateExpired(DateUtil.getNextWeek());
        }
        if (dto.getDateProximity() == RequestDateSelect.thismonth.value) {
            //Este Mês
            log.debug("Este Mês: {}", sessionDTO);
            sessionDTO.setDateExpired(DateUtil.getThisMonth());
        }
        if (dto.getDateProximity() == RequestDateSelect.nextmonth.value) {
            //Proximo Mes
            log.debug("Proximo Mês: {}", sessionDTO);
            sessionDTO.setDateExpired(DateUtil.getNextMonth());
        }
        sessionDTO.setDateCreated(DateUtil.getToday());
        log.debug("Passo 2 {}", sessionDTO);
        return "redirect:/requisicoes?passo=3";

    }

    @PostMapping("/passo-3")
    public String saveFormMaxCandidates(HttpSession httpSession, @Validated(JobRequestDTO.RequestMaxCandidatesGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 3 {}", dto);
            log.debug("Errors 3 {}", errors);
            return "client/job-request/wizard-step-03+9";

        }
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setQuantityCandidatorsMax(dto.getQuantityCandidatorsMax());

        log.debug("Passo 3 {}", sessionDTO);
        return "redirect:/requisicoes?passo=4";

    }


    @PostMapping("/passo-4")
    public String saveFormDescription(HttpSession httpSession, @Validated(JobRequestDTO.RequestDescriptionGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {

        if (errors.hasErrors()) {
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
    public String saveFormImagePath(HttpSession httpSession, RedirectAttributes redirectAttributes, JobRequestDTO dto, Model model) throws IOException {


        //persiste na sessão
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setImageFile(dto.getImageFile());


        if (isValidateImage(dto.getImageFile())) {
            File jobImage = Files.createTempFile("temp", dto.getImageFile().getOriginalFilename()).toFile();
            dto.getImageFile().transferTo(jobImage);
            Map data = cloudinary.uploader().upload(jobImage, ObjectUtils.asMap("folder", "jobs"));


            sessionDTO.setImageSession((String) data.get("url"));
            log.debug("Passo 5 {}", sessionDTO);

            return "redirect:/requisicoes?passo=6";
        } else {
            return "client/job-request/wizard-step-login-user";
        }


    }


    @PostMapping("/passo-6")
    public String checkingIfUserExists(HttpSession httpSession, @Validated(UserDTO.RequestUserEmailInfoGroupValidation.class) UserDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 6 {}", errors.getAllErrors());
            return "client/job-request/wizard-step-login-user";

        }

        Optional oUser = userService.findByEmail(dto.getEmail());

        //persiste na sessão
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setEmailClient(dto.getEmail());





        log.debug("Passo 6 {}", sessionDTO);

        if (oUser.isPresent()) {

            redirectAttributes.addFlashAttribute("msg", "Você já possui conta, agora é só realizar o login!");
            redirectAttributes.addFlashAttribute("email", dto.getEmail());
            return "redirect:/entrar";

        } else {

            return "client/job-request/wizard-step-06";
        }


    }

    @PostMapping("/passo-7")
    public String saveFormClientInfo(HttpSession httpSession, @Validated(JobRequestDTO.RequestClientInfoGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 6 {}", errors.getAllErrors());
            return "client/job-request/wizard-step-06";

        }
        //persiste na sessão
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        sessionDTO.setNameClient(dto.getNameClient());
        sessionDTO.setCep(dto.getCep());
        sessionDTO.setEmailClient(dto.getEmailClient());
        sessionDTO.setPhone(dto.getPhone());
        log.debug("Passo 7 {}", sessionDTO);

        return "redirect:/requisicoes?passo=8";

    }


    @PostMapping("/passo-8")
    public String saveFormVerification(HttpSession httpSession, JobRequestDTO dto, RedirectAttributes redirectAttributes, Model model, SessionStatus status) {

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        Expertise exp = null;
        log.debug("Passo Name {}", sessionDTO.getNameClient());
        Client client = clientService.save(new Client(sessionDTO.getNameClient(), sessionDTO.getEmailClient(), sessionDTO.getPhone(), sessionDTO.getCep()));
        Optional<Expertise> oExpertise = expertiseService.findById(sessionDTO.getExpertiseId());

        if (oExpertise.isPresent()) {
            exp = oExpertise.get();
        }

        sessionDTO.setClientConfirmation(true);
        sessionDTO.setDateCreated(DateUtil.getToday());
        sessionDTO.setStatus("Requerido");
        log.debug("Passo 8 {}", sessionDTO);
        JobRequest jobRequest = jobRequestMapper.toEntity(sessionDTO);
        jobRequest.setClient(client);
        jobRequest.setExpertise(exp);
        //jobRequest.setImage(sessionDTO.getImageSession());
        jobRequestService.save(jobRequest);
        redirectAttributes.addFlashAttribute("msg", "Requisição confirmada!");
        status.setComplete();
        return "redirect:/requisicoes?passo=9";


    }

    @PostMapping("/passo-9")
    public String formConfirmation(HttpSession httpSession, BindingResult errors, JobRequestDTO dto, RedirectAttributes redirectAttributes, Model model, SessionStatus status) {
        return "redirect:/requisicoes";
    }


    public boolean isValidateImage(MultipartFile image) {
        List<String> contentTypes = Arrays.asList("image/png", "image/jpg", "image/jpeg");

        for (int i = 0; i < contentTypes.size(); i++) {
            if (image.getContentType().toLowerCase().startsWith(contentTypes.get(i))) {
                return true;
            }
        }

        return false;
    }
}
