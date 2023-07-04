package br.edu.utfpr.servicebook.controller.company;

import br.edu.utfpr.servicebook.controller.admin.ExpertiseController;
import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.JobOpeningsMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.CompanyService;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.JobOpeningsService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequestMapping("/c/vagas-de-emprego")
@Controller
public class JobOpeningsController {

        public static final Logger log =
                LoggerFactory.getLogger(ExpertiseController.class);

        @Autowired
        private JobOpeningsService jobOpeningsService;

        @Autowired
        private PaginationUtil paginationUtil;

        @Autowired
        private JobOpeningsMapper jobOpeningsMapper;

        @Autowired
        private ExpertiseService expertiseService;

        @Autowired
        private ExpertiseMapper expertiseMapper;

        @Autowired
        private IAuthentication authentication;

        @Autowired
        private UserService userService;

        @Autowired
        private CompanyService companyService;
        @GetMapping
        @RolesAllowed({RoleType.COMPANY})
        public ModelAndView showForm(HttpServletRequest request,
                                     @RequestParam(value = "pag", defaultValue = "1") int page,
                                     @RequestParam(value = "siz", defaultValue = "5") int size,
                                     @RequestParam(value = "ord", defaultValue = "title") String order,
                                     @RequestParam(value = "dir", defaultValue = "ASC") String direction){

            ModelAndView mv = new ModelAndView("company/job-openings-register");

            PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
            Page<JobOpenings> jobPage = jobOpeningsService.findAll(pageRequest);

            List<JobOpeningsDTO> JobOpeningsDTOS = jobPage.stream()
                    .map(s -> jobOpeningsMapper.toDto(s))
                    .collect(Collectors.toList());


            mv.addObject("jobOpenings", JobOpeningsDTOS);
            mv.addObject("expertises", listExpertiseDTO());

            PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobPage);
            mv.addObject("pagination", paginationDTO);
            return mv;
        }

        private List<ExpertiseDTO> listExpertiseDTO(){
            List<Expertise> expertises = expertiseService.findAll();

            List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                    .map(st -> expertiseMapper.toResponseDto(st))
                    .collect(Collectors.toList());

            return expertiseDTOs;
        }

    @PostMapping
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView save(@Valid JobOpeningsDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        Optional<User> userCompany = userService.findByEmail(authentication.getEmail());
        Optional<Company> oCompany = companyService.findById(userCompany.get().getId());

        Optional<Expertise> oExpertise = expertiseService.findById(dto.getExpertiseId());

        if (!oCompany.isPresent()) {
            throw new EntityNotFoundException("A Empresa não foi encontrada!");
        }

        if (!oExpertise.isPresent()) {
            throw new EntityNotFoundException("A especialidade não foi encontrado!");
        }

        JobOpenings jobOpenings = jobOpeningsMapper.toEntity(dto);
        jobOpenings.setCompany(oCompany.get());
        jobOpenings.setExpertise(oExpertise.get());

        jobOpeningsService.save(jobOpenings);

        redirectAttributes.addFlashAttribute("msg", "Vaga de emprego salva com sucesso!");

        return new ModelAndView("redirect:/c/vagas-de-emprego");
    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView showFormForUpdate(@PathVariable("id") Long id, HttpServletRequest request,
                                          @RequestParam(value = "pag", defaultValue = "1") int page,
                                          @RequestParam(value = "siz", defaultValue = "4") int size,
                                          @RequestParam(value = "ord", defaultValue = "title") String order,
                                          @RequestParam(value = "dir", defaultValue = "ASC") String direction){

        ModelAndView mv = new ModelAndView("company/job-openings-register");

        if(id < 0){
            throw new InvalidParamsException("O identificador não pode ser negativo.");
        }

        Optional<JobOpenings> oJobOpeningsService = jobOpeningsService.findById(id);

        if(!oJobOpeningsService.isPresent()){
            throw new EntityNotFoundException("A vaga de emprego não foi encontrada!");
        }

        JobOpeningsDTO jobOpeningsDTO = jobOpeningsMapper.toDto(oJobOpeningsService.get());
        mv.addObject("dto", jobOpeningsDTO);

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        Page<JobOpenings> jobOpeningsPage = jobOpeningsService.findAll(pageRequest);
        List<JobOpeningsDTO> jobOpeningsDTOS = jobOpeningsPage.stream()
                .map(s -> jobOpeningsMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("jobOpenings", jobOpeningsDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobOpeningsPage, "/vagas-de-emprego/" + id);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.COMPANY})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional <JobOpenings> oJobOpenings = this.jobOpeningsService.findById(id);
        JobOpeningsDTO jobOpeningsDTO = jobOpeningsMapper.toDto(oJobOpenings.get());

        if(!oJobOpenings.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        try{
            this.jobOpeningsService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Vaga de emprego removida com sucesso!");
            return "redirect:/c/vagas-de-emprego";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "Vaga de emprego não pode ser removida!");
            return "redirect:/c/vagas-de-emprego";
        }
    }

    private ModelAndView errorFowarding(JobOpeningsDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("company/job-openings-register");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<JobOpenings> jobOpeningsPage = jobOpeningsService.findAll(pageRequest);

        List<JobOpeningsDTO> jobOpeningsDTOS = jobOpeningsPage.stream()
                .map(s -> jobOpeningsMapper.toDto(s))
                .collect(Collectors.toList());

        mv.addObject("jobOpenings", jobOpeningsDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobOpeningsPage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }
}
