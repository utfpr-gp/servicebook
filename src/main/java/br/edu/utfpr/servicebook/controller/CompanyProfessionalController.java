package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.model.repository.UserRepository;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.sse.EventSSE;
import br.edu.utfpr.servicebook.sse.EventSSEDTO;
import br.edu.utfpr.servicebook.sse.EventSseMapper;
import br.edu.utfpr.servicebook.sse.SSEService;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
import com.cloudinary.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@RequestMapping("/minha-conta/empresa/profissionais")
@Controller
public class CompanyProfessionalController {
    public static final Logger log = LoggerFactory.getLogger(CompanyProfessionalController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyProfessionalService companyProfessionalService;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyProfessionalMapper companyProfessionalMapper;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private UserCodeMapper userCodeMapper;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private StateService stateService;

    @Autowired
    private SSEService sseService;

    @Autowired
    private EventSseMapper eventSseMapper;
    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;
    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    /**
     * Apresenta a tela para a empresa adicionar profissionais.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping()
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView showProfessionals(@RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId)  throws Exception {

        User company = this.getCompany();
        UserDTO professionalMinDTO = userMapper.toDto(company);

        ModelAndView mv = new ModelAndView("company/new-professional");

        UserTemplateInfo userTemplateInfo = templateUtil.getUserInfo(professionalMinDTO);
        UserTemplateStatisticInfo sidePanelStatisticDTO = templateUtil.getCompanyStatisticInfo(company, expertiseId.get());

        mv.addObject("statisticInfo", sidePanelStatisticDTO);

        mv.addObject("id", expertiseId.orElse(0L));

        List<User> professionals = userService.findProfessionalsNotExist();

        List<CompanyProfessional> companyProfessionals = companyProfessionalService.findByCompany(company.getId());

        List<CompanyProfessionalDTO2> companyProfessionalDTO2s = companyProfessionals.stream()
                .map(s -> companyProfessionalMapper.toResponseDTO(s))
                .collect(Collectors.toList());

        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        UserTemplateInfo individualInfo = templateUtil.getUserInfo(professionalMinDTO);

        //envia a notificação ao usuário
        List<EventSSE> eventSsesList = sseService.findPendingEventsByEmail(authentication.getEmail());
        List<EventSSEDTO> eventSSEDTOs = eventSsesList.stream()
                .map(eventSse -> {
                    return eventSseMapper.toFullDto(eventSse);
                })
                .collect(Collectors.toList());
        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());

        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());


        mv.addObject("professionals", professionals);
        mv.addObject("professionalCompanies", companyProfessionalDTO2s);
        mv.addObject("statisticInfo", sidePanelStatisticDTO);
        mv.addObject("eventsse", eventSSEDTOs);
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("company", true);

        return mv;
    }

    @PostMapping()
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView saveProfessionals(@Valid CompanyProfessionalDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView mv = new ModelAndView("redirect:profissionais");
        String ids = dto.getIds();
        User company = this.getCompany();
        Optional<User> company_name = userService.findById(company.getId());
        Optional<User> oCompany = (userService.findByEmail(authentication.getEmail()));

        Optional<User> oProfessional = userService.findByEmail(ids);
        if(!oProfessional.isPresent()){
            Random random = new Random();
            int numberRandom = random.nextInt(100 - 1) + 1;

            String token = "RP0"+company.getId()+numberRandom;
            UserTokenDTO userTokenDTO = new UserTokenDTO();
            userTokenDTO.setUser(company);
            userTokenDTO.setEmail(ids);
            userTokenDTO.setToken(token);
            UserToken userToken = userTokenMapper.toEntity(userTokenDTO);

            userTokenService.save(userToken);

            String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString()
                    + "/cadastrar-se?passo-1&code="+token;

            quartzService.sendEmailToRegisterUser(dto.getIds(), company.getName(), tokenLink);
            Optional<UserToken> optionalUserToken = userTokenService.findByEmail(ids);

//            CompanyProfessional p = companyProfessionalService.save(new CompanyProfessional(company, dto.getIds()));
//            redirectAttributes.addFlashAttribute("msg", "Convite enviado para usuário, aguardando confirmação!");

        } else {
            String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/confirmar?empresa=" + company_name.get().getName() +"&email=" + dto.getIds();
            quartzService.sendEmailWithConfirmationUser(dto.getIds(), company.getName(), tokenLink);

            Optional<CompanyProfessional> optionalCompanyProfessional = companyProfessionalService.findByCompanyAndProfessional(company, oProfessional.get());

            if(!optionalCompanyProfessional.isPresent()){
                CompanyProfessional p = companyProfessionalService.save(new CompanyProfessional(company, oProfessional.get(), false));
                redirectAttributes.addFlashAttribute("msg", "Convite enviado para usuário, aguardando confirmação!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Usuário ja está na empresa!");
            }
        }
        return mv;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.COMPANY})
    @Transactional
    public String delete(@PathVariable User id, RedirectAttributes redirectAttributes) throws Exception {
        User company = this.getCompany();
        Optional<User> oProfessional = userService.findById(id.getId());

        Optional<CompanyProfessional> optionalCompanyProfessional = companyProfessionalService.findByCompanyAndProfessional(company, oProfessional.get());

        if (optionalCompanyProfessional.get().getProfessional().getId().equals(id.getId())) {
            this.companyProfessionalService.delete(optionalCompanyProfessional.get().getId());
        }
        return "redirect:/minha-conta/empresa/profissionais";
    }

    /**
     * Retorna a empresa logado.
     * @return
     * @throws Exception
     */
    private User getCompany() throws Exception {
        Optional<User> oCompany = (userService.findByEmail(authentication.getEmail()));

        if (!oCompany.isPresent()) {
            throw new Exception("Opss! Não foi possivel encontrar seus dados, tente fazer login novamente");
        }

        return oCompany.get();
    }
}