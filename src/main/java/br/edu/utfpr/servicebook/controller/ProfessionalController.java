package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
import br.edu.utfpr.servicebook.model.mapper.UserMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @Value("${pagination.size}")
    private Integer paginationSize;

    @Value("${pagination.size.visitor}")
    private Integer paginationSizeVisitor;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private PaginationUtil paginationUtil;

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
     * @param searchTerm
     * @param page
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/busca")
    @PermitAll
    protected ModelAndView showSearchResults(
            @RequestParam(value = "termo-da-busca") String searchTerm,
            @RequestParam(value = "pag", defaultValue = "1") int page
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

        Page<Individual> professionals = individualService.findDistinctByTermIgnoreCaseWithPagination(searchTerm, page, size);
        List<ProfessionalSearchItemDTO> professionalSearchItemDTOS = professionals.stream()
                .map(s -> individualMapper.toSearchItemDto(s, individualService.getExpertises(s)))
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(professionals, "/profissionais/busca?termo-da-busca="+ searchTerm);
        mv.addObject("professionals", professionalSearchItemDTOS);
        mv.addObject("pagination", paginationDTO);
        mv.addObject("isParam", true);
        mv.addObject("searchTerm", searchTerm);

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
        ModelAndView mv = new ModelAndView("visitor/professional-details");

        Optional<User> oProfessional = userService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado.");
        }

        Optional<User> oClientAuthenticated = (userService.findByEmail(authentication.getEmail()));
        mv.addObject("logged", oClientAuthenticated.isPresent());

        //se o cliente está logado, mostra se ele segue o profissional
        if(oClientAuthenticated.isPresent()){
            List<Follows> follows = followsService.findFollowProfessionalClient(oProfessional.get(), oClientAuthenticated.get());
            boolean isFollow = !follows.isEmpty();
            UserDTO clientDTO = userMapper.toDto(oClientAuthenticated.get());
            mv.addObject("isFollow", isFollow);
            mv.addObject("client", clientDTO);
        }

        List<ExpertiseDTO> expertiseDTOs = userService.getExpertiseDTOs(oProfessional.get());

        UserDTO professionalDTO = userMapper.toDto(oProfessional.get());

        mv.addObject("professional", professionalDTO);
        mv.addObject("professionalExpertises", expertiseDTOs);
        return mv;
    }
}
