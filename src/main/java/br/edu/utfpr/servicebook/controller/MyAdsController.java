package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ServiceMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequestMapping("/minha-conta/profissional/meus-anuncios")
@Controller
public class MyAdsController {
    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private ProfessionalServiceOfferingService professionalServiceOfferingService;

    @Autowired
    private UserService userService;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private ProfessionalServicePackageOfferingService professionalServicePackageOfferingService;

    @Autowired
    private ProfessionalServiceOfferingAdItemService professionalServiceOfferingAdItemService;

    @GetMapping
    @PermitAll
    protected ModelAndView showMyAds() throws Exception {
        ModelAndView mv = new ModelAndView("professional/my-ads");
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);

        Page<Expertise> expertisePage = expertiseService.findAll(pageRequest);

        List<ExpertiseDTO> expertiseDTOS = expertisePage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        List<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findProfessionalServiceOfferingByUser(oUser.get());
        List<ProfessionalServiceOffering> servicesIndividuals = professionalServiceOfferingService.findFirst3ProfessionalServiceOfferingByUserAndType(oUser.get(), ProfessionalServiceOffering.Type.INDIVIDUAL);

        List<ProfessionalServicePackageOffering> servicesPackages = professionalServicePackageOfferingService.findAllByUserAndType(oUser.get(), ProfessionalServicePackageOffering.Type.SIMPLE_PACKAGE);
        List<ProfessionalServicePackageOffering> servicesCombined = professionalServicePackageOfferingService.findByTypeAndUser(oUser.get(), ProfessionalServicePackageOffering.Type.COMBINED_PACKAGE);
        List<ProfessionalServiceOfferingAdItem> teste = professionalServiceOfferingAdItemService.findAllByProfessionalServicePackageOfferingUser(oUser.get());

        mv.addObject("expertises", expertiseDTOS);
        mv.addObject("professionalServiceOfferings", professionalServiceOfferings);
        mv.addObject("servicesIndividuals", servicesIndividuals);
        mv.addObject("servicesPackages", servicesPackages);
        mv.addObject("servicesCombined", servicesCombined);
        mv.addObject("teste", teste);
        return mv;
    }

    @GetMapping("/novo")
    @PermitAll
    protected ModelAndView registerMyAds() throws Exception {
        ModelAndView mv = new ModelAndView("professional/my-ads-register");

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());
//        mv.addObject("services", serviceDTOS);
        return mv;
    }

    /**
     * Adiciona um serviço individual.
     * @param professionalServiceOfferingDTO
     * @param errors
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @PostMapping("/novo/individual")
    @PermitAll
    public String saveAds(ProfessionalServiceOfferingDTO professionalServiceOfferingDTO, BindingResult errors, RedirectAttributes
        redirectAttributes) throws Exception {
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));

        ModelAndView mv = new ModelAndView("professional/my-ads-register");

            ProfessionalServiceOffering professionalServiceOffering = new ProfessionalServiceOffering();
            Optional<Service> oService = serviceService.findById(professionalServiceOfferingDTO.getServiceId());
            professionalServiceOffering.setUser(oUser.get());
            professionalServiceOffering.setPrice(professionalServiceOfferingDTO.getPrice());
            professionalServiceOffering.setUnit(professionalServiceOfferingDTO.getUnit());
            professionalServiceOffering.setDuration(professionalServiceOfferingDTO.getDuration());
            professionalServiceOffering.setType(ProfessionalServiceOffering.Type.INDIVIDUAL);

            professionalServiceOffering.setDescription(professionalServiceOfferingDTO.getDescription());
            if(oService.isPresent()){
                professionalServiceOffering.setService(oService.get());
            }

        //grava o nome do serviço original
            professionalServiceOffering.setName(oService.get().getName());
            professionalServiceOfferingService.save(professionalServiceOffering);

        return("redirect:/minha-conta/profissional/meus-anuncios");
    }

    /**
     * Adiciona um pacote de serviço.
     * @param professionalServicePackageOfferingDTO
     * @param professionalServiceOfferingDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/novo/pacote")
    @PermitAll
    public String saveAdsPackage(ProfessionalServiceOfferingDTO professionalServiceOfferingDTO, ProfessionalServicePackageOfferingDTO professionalServicePackageOfferingDTO, BindingResult errors, RedirectAttributes
            redirectAttributes) throws Exception {
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));
        ModelAndView mv = new ModelAndView("professional/my-ads-register");

        Optional<Service> oService = serviceService.findById(professionalServicePackageOfferingDTO.getServiceId());

        ProfessionalServicePackageOffering professionalServicePackageOffering = new ProfessionalServicePackageOffering();
        professionalServicePackageOffering.setPrice(professionalServicePackageOfferingDTO.getPrice());
        professionalServicePackageOffering.setType(ProfessionalServicePackageOffering.Type.SIMPLE_PACKAGE);
        professionalServicePackageOffering.setUnit(professionalServicePackageOfferingDTO.getUnit());
        professionalServicePackageOffering.setDuration(professionalServicePackageOfferingDTO.getDuration());
        professionalServicePackageOffering.setDescription(professionalServicePackageOfferingDTO.getDescription());
        //grava o nome do serviço original
        professionalServicePackageOffering.setName(oService.get().getName());
        professionalServicePackageOffering.setAmount(professionalServicePackageOfferingDTO.getAmount());
        professionalServicePackageOffering.setUser(oUser.get());
        professionalServicePackageOffering.setService(oService.get());

        professionalServicePackageOfferingService.save(professionalServicePackageOffering);

        return("redirect:/minha-conta/profissional/meus-anuncios");
    }
    /**
     * Adiciona um serviço combinado com um ou mais serviços.
     * @param professionalServicePackageOfferingDTO
     * @param professionalServiceOfferingDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/novo/combinado")
    @PermitAll
    public String saveAdsCombined(ProfessionalServiceOfferingDTO professionalServiceOfferingDTO, ProfessionalServicePackageOfferingDTO professionalServicePackageOfferingDTO, BindingResult errors, RedirectAttributes
            redirectAttributes) throws Exception {
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));
        Optional<Expertise> oExpertise = expertiseService.findById(professionalServicePackageOfferingDTO.getExpertiseId());

        ModelAndView mv = new ModelAndView("professional/my-ads-register");
        ProfessionalServicePackageOffering professionalServicePackageOffering = new ProfessionalServicePackageOffering();
        professionalServicePackageOffering.setPrice(professionalServicePackageOfferingDTO.getPrice());
        professionalServicePackageOffering.setType(ProfessionalServicePackageOffering.Type.COMBINED_PACKAGE);
        professionalServicePackageOffering.setUnit(professionalServicePackageOfferingDTO.getUnit());
        professionalServicePackageOffering.setDuration(professionalServicePackageOfferingDTO.getDuration());
        professionalServicePackageOffering.setDescription(professionalServicePackageOfferingDTO.getDescription());
        professionalServicePackageOffering.setName(professionalServicePackageOfferingDTO.getDescription());
        professionalServicePackageOffering.setExpertise(oExpertise.get());
        professionalServicePackageOffering.setName(professionalServicePackageOfferingDTO.getDescription());

        //grava o nome do serviço original
        professionalServicePackageOffering.setUser(oUser.get());

        professionalServicePackageOfferingService.save(professionalServicePackageOffering);

            for (Long valor : professionalServiceOfferingDTO.getDescriptions()) {
                ProfessionalServiceOffering professionalServiceOffering = new ProfessionalServiceOffering();
                 Optional<Service> oService = serviceService.findById(valor);

                professionalServiceOffering.setDescription(professionalServicePackageOfferingDTO.getDescription());
               //grava o nome do serviço original
                professionalServiceOffering.setName(oService.get().getName());
                professionalServiceOffering.setType(ProfessionalServiceOffering.Type.COMBINED_PACKAGE);
                professionalServiceOffering.setUnit(professionalServiceOfferingDTO.getUnit());
                professionalServiceOffering.setDuration(professionalServiceOfferingDTO.getDuration());
                professionalServiceOffering.setPrice(professionalServiceOfferingDTO.getPrice());
                professionalServiceOffering.setUser(oUser.get());
                professionalServiceOffering.setService(oService.get());
                professionalServiceOfferingService.save(professionalServiceOffering);
//
                ProfessionalServiceOfferingAdItem professionalServiceOfferingAdItem = new ProfessionalServiceOfferingAdItem(professionalServiceOffering, professionalServicePackageOffering);
                professionalServiceOfferingAdItemService.save(professionalServiceOfferingAdItem);

            }

        return("redirect:/minha-conta/profissional/meus-anuncios");
    }
    @GetMapping("/pacotes")
    @PermitAll
    protected ModelAndView showMyAdsPackages() throws Exception {
        ModelAndView mv = new ModelAndView("professional/ads/my-ads-packages");
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        List<ProfessionalServicePackageOffering> servicesPackages = professionalServicePackageOfferingService.findByTypeAndUser(oUser.get(), ProfessionalServicePackageOffering.Type.SIMPLE_PACKAGE);

        mv.addObject("services", serviceDTOS);
        mv.addObject("servicesPackages", servicesPackages);
        return mv;
    }

    @GetMapping("/individuais")
    @PermitAll
    protected ModelAndView showMyAdsIndividuals() throws Exception {
        ModelAndView mv = new ModelAndView("professional/ads/my-ads-individuals");
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        List<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findProfessionalServiceOfferingByUserAndType(oUser.get(), ProfessionalServiceOffering.Type.INDIVIDUAL);

        mv.addObject("services", serviceDTOS);
        mv.addObject("professionalServiceOfferings", professionalServiceOfferings);
        return mv;
    }

    @GetMapping("/combinados")
    @PermitAll
    protected ModelAndView showMyAdsCombineds() throws Exception {
        ModelAndView mv = new ModelAndView("professional/ads/my-ads-combineds");
        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        List<ProfessionalServicePackageOffering> servicesCombined = professionalServicePackageOfferingService.findByTypeAndUser(oUser.get(), ProfessionalServicePackageOffering.Type.COMBINED_PACKAGE);

        mv.addObject("services", serviceDTOS);
        mv.addObject("servicesCombined", servicesCombined);
        return mv;
    }
    @GetMapping("/especialidade/{expertiseId}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    @ResponseBody
    public List<ProfessionalServiceOffering> findAdsByExpertise(@PathVariable Long expertiseId)  throws Exception {

        User professional = this.getAuthenticatedUser();

        Expertise expertise = expertiseService.findById(expertiseId).orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));

        //lista de serviços
        List<Service> services = serviceService.findByExpertise(expertise);
        List<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findProfessionalServiceOfferingByUserAndExpertise(professional.getId(), expertiseId);

        return professionalServiceOfferings;
    }
    /**
     * Retorna o indivíduo logado.
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