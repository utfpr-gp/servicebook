package br.edu.utfpr.servicebook.controller.company;

import br.edu.utfpr.servicebook.controller.admin.ExpertiseController;
import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.JobMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
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
public class JobController {

    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private JobMapper jobMapper;

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

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    /**
     * Mostra o formulário de cadastro de vagas de emprego
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     */
    @GetMapping
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView showForm(@RequestParam(value = "pag", defaultValue = "1") int page,
                                 @RequestParam(value = "siz", defaultValue = "5") int size,
                                 @RequestParam(value = "ord", defaultValue = "title") String order,
                                 @RequestParam(value = "dir", defaultValue = "ASC") String direction){

        ModelAndView mv = new ModelAndView("company/job-openings-register");
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);

        Optional<User> oUser = userService.findByEmail(authentication.getEmail());
        Page<Job> jobPage = jobService.findByCompany(oUser.get().getId(), pageRequest);

        List<JobDTO> jobOpeningsDTOs = jobPage.stream()
                .map(s -> jobMapper.toDto(s))
                .collect(Collectors.toList());

        mv.addObject("jobs", jobOpeningsDTOs);

        //especialidades da empresa
        List<ProfessionalExpertise> expertises = professionalExpertiseService.findByProfessional(oUser.get());
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(st -> expertiseMapper.toResponseDto(st.getExpertise()))
                .collect(Collectors.toList());

        mv.addObject("expertises", expertiseDTOs);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobPage);
        mv.addObject("pagination", paginationDTO);
        return mv;
    }



    /**
     * Método responsável por salvar uma vaga de emprego
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     */
    @PostMapping
    @RolesAllowed({RoleType.COMPANY})
    public String save(@Valid JobDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            redirectAttributes.addFlashAttribute("dto", dto);
            return "redirect:/c/vagas-de-emprego";
        }

        Optional<User> oUser = userService.findByEmail(authentication.getEmail());
        Optional<Company> oCompany = companyService.findById(oUser.get().getId());

        if (!oCompany.isPresent()) {
            throw new EntityNotFoundException("A Empresa não foi encontrada!");
        }

        Optional<Expertise> oExpertise = expertiseService.findById(dto.getExpertiseId());

        if (!oExpertise.isPresent()) {
            errors.rejectValue("expertiseId","error.dto.expertiseId.not-found","A especialidade não foi encontrada!");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            redirectAttributes.addFlashAttribute("dto", dto);
            return "redirect:/c/vagas-de-emprego";
        }

        //verifica se a empresa tem esta especialidade
        if(!professionalExpertiseService.findByProfessionalAndExpertise(oCompany.get(), oExpertise.get()).isPresent()){
            errors.rejectValue("expertiseId","error.dto.expertiseId.not-exists","A empresa não possui esta especialidade!");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            redirectAttributes.addFlashAttribute("dto", dto);
            return "redirect:/c/vagas-de-emprego";
        }

        Job job = jobMapper.toEntity(dto);
        job.setCompany(oCompany.get());
        job.setExpertise(oExpertise.get());

        jobService.save(job);

        redirectAttributes.addFlashAttribute("msg", "Vaga de emprego salva com sucesso!");

        return "redirect:/c/vagas-de-emprego";
    }

    /**
     * Mostra o formulário de edição de vagas de emprego
     * @param id
     * @param request
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     */
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

        //verifica se a vaga de emprego existe
        Optional<Job> oJob = jobService.findById(id);

        if(!oJob.isPresent()){
            throw new EntityNotFoundException("A vaga de emprego não foi encontrada!");
        }

        JobDTO jobOpeningsDTO = jobMapper.toDto(oJob.get());
        mv.addObject("dto", jobOpeningsDTO);

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);

        Optional<User> oUser = userService.findByEmail(authentication.getEmail());
        Page<Job> jobPage = jobService.findByCompany(oUser.get().getId(), pageRequest);

        List<JobDTO> jobOpeningsDTOs = jobPage.stream()
                .map(s -> jobMapper.toDto(s))
                .collect(Collectors.toList());

        mv.addObject("jobs", jobOpeningsDTOs);

        //especialidades da empresa
        List<ProfessionalExpertise> expertises = professionalExpertiseService.findByProfessional(oUser.get());
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(st -> expertiseMapper.toResponseDto(st.getExpertise()))
                .collect(Collectors.toList());

        mv.addObject("expertises", expertiseDTOs);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobPage, "/vagas-de-emprego/" + id);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    /**
     * Remove uma vaga de emprego
     * @param id
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.COMPANY})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional <Job> oJob = this.jobService.findById(id);

        if(!oJob.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        JobDTO jobDTO = jobMapper.toDto(oJob.get());

        try{
            this.jobService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "A vaga de emprego foi removida com sucesso!");
            return "redirect:/c/vagas-de-emprego";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "A vaga de emprego não pode ser removida!");
            return "redirect:/c/vagas-de-emprego";
        }
    }
}
