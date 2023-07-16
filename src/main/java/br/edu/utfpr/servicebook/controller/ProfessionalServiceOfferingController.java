package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ChooseExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalServiceOfferingDTO;
import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/minha-conta/profissional/servicos")
@Controller
public class ProfessionalServiceOfferingController {
    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ProfessionalServiceOfferingService professionalServiceOfferingService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private ProfessionalServiceOfferingMapper ProfessionalServiceOfferingMapper;

    @Autowired
    private IAuthentication authentication;

    /**
     * Retorna a lista de serviços oferecidos pelo profissional.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showServices(@PathVariable Long id)  throws Exception {

        User user = this.getAuthenticatedUser();

        Expertise expertise = expertiseService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));

        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(expertise);

        List<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findProfessionalServiceOfferingByUserAndService_Expertise(user, expertise);

        //transforma a lista de ofertas de serviços em uma lista de DTOs com stream
        List<ProfessionalServiceOfferingDTO> professionalServiceOfferingsDTO = professionalServiceOfferings.stream()
                .map(professionalServiceOffering -> ProfessionalServiceOfferingMapper.toDTO(professionalServiceOffering))
                .toList();

        ModelAndView mv = new ModelAndView("professional/my-services");
        mv.addObject("expertise", expertiseDTO);
        mv.addObject("professionalServiceOfferings", professionalServiceOfferingsDTO);

        return mv;
    }

    /**
     * Retorna o formulário para cadastrar um novo serviço para uma das especialidades do profissional.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/novo/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showServicesFormToRegister(@PathVariable Long id)  throws Exception {

        User user = this.getAuthenticatedUser();

        Expertise expertise = expertiseService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));

        List<Service> services = serviceService.findByExpertise(expertise);
        List<ServiceDTO> servicesDTO = services.stream()
                .map(service -> serviceMapper.toDto(service))
                .toList();

        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(expertise);

        ModelAndView mv = new ModelAndView("professional/my-services-register");
        mv.addObject("expertise", expertiseDTO);
        mv.addObject("services", servicesDTO);

        return mv;
    }

    @PostMapping()
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveService(@Validated ServiceDTO serviceDTO, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {

        String routeRedirect = "redirect:/minha-conta/profissional/servicos/novo/" + serviceDTO.getExpertiseId();
        if (errors.hasErrors()) {
            log.error("Erro de validação de especialidade");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return routeRedirect;
        }

        //verifica se o serviço existe, se foi cadastrado pelo administrador
        Long serviceId = serviceDTO.getId();
        Optional<Service> oService = serviceService.findById(serviceId);
        if(!oService.isPresent()){
            log.error("Serviço não encontrado");
            errors.rejectValue("id", "service.not.found", "Serviço não encontrado");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return routeRedirect;
        }

        //verifica se já existe o serviço cadastrado pelo nome
        Optional<ProfessionalServiceOffering> oProfessionalServiceOffering = professionalServiceOfferingService.findProfessionalServiceOfferingByName(serviceDTO.getName());

        if(oProfessionalServiceOffering.isPresent()){
            log.error("Serviço já cadastrado");
            errors.rejectValue("name", "service.already.registered", "Serviço já cadastrado");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return routeRedirect;
        }

        User professional = this.getAuthenticatedUser();

        ProfessionalServiceOffering professionalServiceOffering = new ProfessionalServiceOffering();
        professionalServiceOffering.setName(serviceDTO.getName());
        professionalServiceOffering.setDescription(serviceDTO.getDescription());
        professionalServiceOffering.setUser(professional);
        professionalServiceOffering.setService(oService.get());

        professionalServiceOfferingService.save(professionalServiceOffering);

        return routeRedirect;
    }


    /**
     * Retorna o usuário logado.
     * @return
     * @throws Exception
     */
    private User getAuthenticatedUser() throws Exception {
        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        return oProfessional.get();
    }

}
