package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/profissionais")
public class ProfessionalController {

    @Autowired
    private IndividualService individualService;

    @Autowired
    private ProfessionalServiceOfferingService professionalServiceOfferingService;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private FollowsService followsService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfessionalServiceOfferingMapper professionalServiceOfferingMapper;

    @Value("${pagination.size}")
    private Integer paginationSize;

    @Value("${pagination.size.visitor}")
    private Integer paginationSizeVisitor;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private ExpertiseMapper expertiseMapper;

    @GetMapping
    @PermitAll
    protected ModelAndView showAll() throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        Optional<Individual> oIndividual = (individualService.findByEmail(authentication.getEmail()));
        IndividualDTO individualDTO = individualMapper.toDto(oIndividual.get());
        mv.addObject("professional", individualDTO);
        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        List<JobContracted> jobContracted = jobContractedService.findByIdProfessional(individualDTO.getId());
        List<JobContractedDTO> JobContractedDTO = jobContracted.stream()
                .map(contracted -> jobContractedMapper.toResponseDto(contracted))
                .collect(Collectors.toList());
        List<Individual> professionals = individualService.findAll();
        Map<Long, List> professionalsExpertises = new HashMap<>();

        for (Individual professional : professionals) {
            List<ExpertiseDTO> expertisesDTO = individualService.getExpertises(professional);
            professionalsExpertises.put(professional.getId(), expertisesDTO);
        }

        mv.addObject("professionals", professionals);
        mv.addObject("professionalsExpertises", professionalsExpertises);

        return mv;
    }

    /**
     * Retorna a lista de profissionais de acordo com o termo de busca.
     * Se estiver logado, o usuário poderá ter acesso a todos os profissionais de acordo com a sua busca.
     * Caso seja um visitante, terá acesso a apenas 4 profissionais.
     * @param searchService
     * @param page
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/busca")
    @PermitAll
    protected ModelAndView showSearchResults(
//            @RequestParam(value = "termo-da-busca") String searchTerm,
            @RequestParam(defaultValue = "0", value = "serviceId") Long searchService,
            @RequestParam(defaultValue = "", value = "expertiseId") Long searchExpertise,
            @RequestParam(defaultValue = "", value = "categoryId") Long searchCategory,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        //quando o usuário está logado, o tamanho da página é maior de quando é visitante
        Integer size = this.paginationSize;
        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));
        mv.addObject("logged", individual.isPresent());

        //quando o usuário é visitante, apresenta apenas 4 resultados, por isso que sempre será a primeira página
        if (!individual.isPresent()) {
            page = 1;
            size = this.paginationSizeVisitor;
        }
        if(searchService == 0){
            redirectAttributes.addFlashAttribute("msg", "A atualização foi salva com sucesso!");
        } else {

            Optional<Service> service = serviceService.findById(searchService);
            Optional<Expertise> expertise = expertiseService.findById(searchExpertise);
            Optional<Category> category = categoryService.findById(searchCategory);

            Page<Individual> professionals = individualService.findAllIndividualsByService(service.get(), page, size);
            Page<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findAllIndividualsByService(service.get(), page, size);

            List<ProfessionalSearchItemDTO> professionalSearchItemDTOS = professionals.stream()
                    .map(s -> individualMapper.toSearchItemDto(s, individualService.getExpertises(s)))
                    .collect(Collectors.toList());

            List<ProfessionalServiceOfferingDTO> professionalServiceOfferingDTOS = professionalServiceOfferings.stream()
                    .map(s -> professionalServiceOfferingMapper.toDTO(s)).collect(Collectors.toList());

            //lista de categorias
            List<Category> categories = categoryService.findAll();
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(s -> categoryMapper.toDto(s))
                    .collect(Collectors.toList());

            PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(professionals, "/profissionais/busca?categoryId=" + searchCategory + "&expertiseId=" + searchExpertise + "&serviceId=" + searchService);

            mv.addObject("professionals", professionalSearchItemDTOS);
            mv.addObject("categoryDTOs", categoryDTOs);
            mv.addObject("pagination", paginationDTO);
            mv.addObject("isParam", true);
            mv.addObject("service", service.get().getName());
            mv.addObject("searchTerm", searchService);
            mv.addObject("dto_expertise", expertise.get());
            mv.addObject("dto", category.get());
            mv.addObject("dto_service", service.get());

            mv.addObject("professionalServiceOfferingDTOS", professionalServiceOfferingDTOS);

        }

        return mv;
    }

    /**
     * Retorna a página de detalhes do profissional.
     * Esta página pode ser acessada de forma autenticada ou anônima.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/detalhes/{id}")
    @PermitAll
    protected ModelAndView showProfessionalDetailsToVisitors(@PathVariable("id") Long id) throws Exception {

        Optional<User> oProfessional = userService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado.");
        }

        //cliente autenticado, caso esteja logado
        Optional<User> oClientAuthenticated = (userService.findByEmail(authentication.getEmail()));

        //profissional requisitado
        UserDTO professionalDTO = userMapper.toDto(oProfessional.get());

        //especialidades do profissional requisitado
        List<ExpertiseDTO> expertisesDTO = userService.getExpertiseDTOs(oProfessional.get());

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());

        //serviços por especialidade
        Map<ProfessionalExpertise, List<ProfessionalServiceOfferingDTO>> servicesByExpertise = new HashMap<>();
        for(ProfessionalExpertise pe : professionalExpertises) {
            List<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findProfessionalServiceOfferingByUserAndExpertise(oProfessional.get().getId(), pe.getExpertise().getId());

            if(professionalServiceOfferings.isEmpty()){
                continue;
            }

            //transforma para DTO
            List<ProfessionalServiceOfferingDTO> professionalServiceOfferingsDTO = professionalServiceOfferings.stream()
                    .map(service -> professionalServiceOfferingMapper.toDTO(service))
                    .collect(Collectors.toList());

            servicesByExpertise.put(pe, professionalServiceOfferingsDTO);
        }

        ModelAndView mv = new ModelAndView("visitor/professional-details");
        mv.addObject("professional", professionalDTO);
        mv.addObject("professionalExpertises", expertisesDTO);
        mv.addObject("logged", oClientAuthenticated.isPresent());
        mv.addObject("servicesByExpertise", servicesByExpertise);

        //se o cliente está logado, mostra se ele segue o profissional
        if(oClientAuthenticated.isPresent()){
            List<Follows> follows = followsService.findFollowProfessionalClient(oProfessional.get(), oClientAuthenticated.get());
            boolean isFollow = !follows.isEmpty();
            UserDTO clientDTO = userMapper.toDto(oClientAuthenticated.get());
            mv.addObject("isFollow", isFollow);
            mv.addObject("client", clientDTO);
        }
        return mv;
    }

    @GetMapping("busca/todos")
    @PermitAll
    protected ModelAndView showProfessionalsServices(
            @RequestParam(defaultValue = "0", value = "serviceId") Long searchService,
            @RequestParam(defaultValue = "", value = "expertiseId") Long searchExpertise,
            @RequestParam(defaultValue = "", value = "categoryId") Long searchCategory) throws Exception {

        //cliente autenticado, caso esteja logado
        Optional<User> oClientAuthenticated = (userService.findByEmail(authentication.getEmail()));

        ModelAndView mv = new ModelAndView("visitor/professionals-services");
        mv.addObject("logged", oClientAuthenticated.isPresent());
        if(!oClientAuthenticated.isPresent()){
            return new ModelAndView("visitor/login");
        }

        Optional<Expertise> expertise = expertiseService.findById(searchExpertise);

        //se o cliente está logado, mostra se ele segue o profissional
        if(oClientAuthenticated.isPresent()){
            UserDTO clientDTO = userMapper.toDto(oClientAuthenticated.get());
            mv.addObject("client", clientDTO);
        }
        Optional<Service> service = serviceService.findById(searchService);

        List<Individual> professionals = individualService.findAllIndividualsByService(service.get());

        List<ProfessionalServiceOffering> professionalServiceOfferings = professionalServiceOfferingService.findAllIndividualsByService(service.get());

        List<ProfessionalSearchItemDTO> professionalSearchItemDTOS = professionals.stream()
                .map(s -> individualMapper.toSearchItemDto(s, individualService.getExpertises(s)))
                .collect(Collectors.toList());

        List<ProfessionalServiceOfferingDTO> professionalServiceOfferingDTOS = professionalServiceOfferings.stream()
                .map(s -> professionalServiceOfferingMapper.toDTO(s)).collect(Collectors.toList());

        Optional<ProfessionalServiceOffering> countProfessionals = professionalServiceOfferingService.countAllByService(service.get());

        mv.addObject("expertise", expertise.get());
        mv.addObject("professionalServiceOfferingDTOS", professionalServiceOfferingDTOS);
        mv.addObject("countProfessionals", countProfessionals.get());

        return mv;
    }
}
