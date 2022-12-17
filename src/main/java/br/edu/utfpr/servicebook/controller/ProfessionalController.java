package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.sse.EventSse;
import br.edu.utfpr.servicebook.sse.EventSseDTO;
import br.edu.utfpr.servicebook.sse.EventSseMapper;
import br.edu.utfpr.servicebook.sse.SSEService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;

import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/profissionais")
public class ProfessionalController {

    @Autowired
    private IndividualService individualService;

    @Autowired
    private CityService cityService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @Autowired
    private IndividualMapper individualMapper;

    @Value("${pagination.size}")
    private Integer paginationSize;

    @Value("${pagination.size.visitor}")
    private Integer paginationSizeVisitor;

    @GetMapping
    protected ModelAndView showAll() throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        Optional<Individual> oIndividual = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
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
    protected ModelAndView showSearchResults(
            @RequestParam(value = "termo-da-busca") String searchTerm,
            @RequestParam(value = "pag", defaultValue = "1") int page
    ) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        //quando o usuário está logado, o tamanho da página é maior de quando é visitante
        Integer size = this.paginationSize;
        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        Optional<Individual> individual = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
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

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(professionals, "/profissionais/busca?termo-da-busca="+ searchTerm);
        mv.addObject("professionals", professionalSearchItemDTOS);
        mv.addObject("pagination", paginationDTO);
        mv.addObject("isParam", true);
        mv.addObject("searchTerm", searchTerm);

        return mv;
    }

    @GetMapping("/detalhes/{id}")
    protected ModelAndView showProfessionalDetailsToVisitors(@PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/professional-details");

        Optional<Individual> oProfessional = individualService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado.");
        }


        Optional<Individual> individual = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
        mv.addObject("logged", individual.isPresent());

        List<ExpertiseDTO> expertisesDTO = individualService.getExpertises(oProfessional.get());

        IndividualDTO professionalDTO = individualMapper.toDto(oProfessional.get());

        mv.addObject("professional", professionalDTO);
        mv.addObject("professionalExpertises", expertisesDTO);
        return mv;
    }


}
