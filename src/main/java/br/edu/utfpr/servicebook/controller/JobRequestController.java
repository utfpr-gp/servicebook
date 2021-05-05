package br.edu.utfpr.servicebook.controller;


import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.service.JobRequestService;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/requisicoes")
@SessionAttributes("wizard")
public class JobRequestController {

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    JobRequestMapper jobRequestMapper;

    @Autowired
    private WizardSessionUtil<JobRequestDTO> wizardSessionUtil;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public String showWizard(@RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
                             HttpSession httpSession,
                             Model model) {
        log.debug("Mostrando o passo {}", step);
        if(step < 1 || step > 8){

            step = 1L;
        }
        JobRequestDTO dto = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        model.addAttribute("dto", dto);

        /*
        if(step == 6L){
            List<JobRequest> jobRequests = jobRequestService.findAll();
            List<JobRequestDTO> jobRequestDTOs = jobRequests.stream()
                    .map(u -> jobRequestMapper.toDto(u))
                    .collect(Collectors.toList());
            model.addAttribute("jobRequestDTOs", jobRequestDTOs);
        }*/

        return "client/job-request/wizard-step-0" + step;
    }


    @PostMapping("/passo-1")
    public String saveFormRequestedJob(HttpSession httpSession, RedirectAttributes redirectAttributes, JobRequestDTO dto, Model model){


        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        sessionDTO.setCategory_id(dto.getCategory_id());


        log.debug("Passo 1 {}", sessionDTO);

        return "redirect:/requisicoes?passo=2";

    }
    @PostMapping("/passo-2")
    public String saveFormDateJob(HttpSession httpSession,RedirectAttributes redirectAttributess, JobRequestDTO dto, Model model){


        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        sessionDTO.setDate_proximidade(dto.getDate_proximidade());

        log.debug("Passo 2 {}", sessionDTO);

        return "redirect:/requisicoes?passo=3";

    }
    @PostMapping("/passo-3")
    public String saveFormMaxCandidates(HttpSession httpSession,RedirectAttributes redirectAttributess, JobRequestDTO dto, Model model){

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        sessionDTO.setQuantity_candidators_max(dto.getQuantity_candidators_max());

        log.debug("Passo 3 {}", sessionDTO);

        return "redirect:/requisicoes?passo=4";

    }


    @PostMapping("/passo-4")
    public String saveFormDescription(HttpSession httpSession, @Validated(JobRequestDTO.RequestDescriptionGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model){

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 4 {}", dto);
            log.debug("Errors 4 {}", errors);

            return "redirect:/requisicoes?passo=4";
        }

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        sessionDTO.setDescription(dto.getDescription());

        log.debug("Passo 4 {}", sessionDTO);

        return "redirect:/requisicoes?passo=5";

    }
    @PostMapping("/passo-5")
    public String saveFormImagePath(HttpSession httpSession, RedirectAttributes redirectAttributes, JobRequestDTO dto, Model model){
        //persiste na sessão
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        sessionDTO.setImage(dto.getImage());

        log.debug("Passo 5 {}", sessionDTO);

        return "redirect:/requisicoes?passo=6";

    }
    @PostMapping("/passo-6")
    public String saveFormClientInfo(HttpSession httpSession, @Validated(JobRequestDTO.RequestClientInfoGroupValidation.class) JobRequestDTO dto, BindingResult errors, RedirectAttributes redirectAttributes, Model model){

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());
            log.debug("Passo 5 {}", errors.getAllErrors());
            return "redirect:/requisicoes?passo=6";

        }
        //persiste na sessão
        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);
        sessionDTO.setCep(dto.getCep());
        sessionDTO.setName(dto.getName());
        sessionDTO.setEmail(dto.getEmail());
        sessionDTO.setPhone(dto.getPhone());

        log.debug("Passo 6 {}", sessionDTO);

        return "redirect:/requisicoes?passo=7";

    }
    @PostMapping("/passo-7")
    public String saveFormVerification(HttpSession httpSession, JobRequestDTO dto, Model model){

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);

        log.debug("Passo 7 {}", sessionDTO);

        return "redirect:/requisicoes?passo=8";

    }
    @PostMapping("/passo-8")
    public String formConfirmation(HttpSession httpSession, BindingResult errors,JobRequestDTO dto, RedirectAttributes redirectAttributes, Model model,SessionStatus status){

        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class);


        log.debug("Passo 8 {}", sessionDTO);
        validator.validate(dto, errors, new Class[]{JobRequestDTO.RequestCategoryIdGroupValidation.class, JobRequestDTO.RequestDateProximidadeGroupValidation.class,  JobRequestDTO.RequestMaxCandidatesGroupValidation.class,  JobRequestDTO.RequestClientInfoGroupValidation.class,  JobRequestDTO.RequestDescriptionGroupValidation.class});
        JobRequest jobRequest = jobRequestMapper.toEntity(dto);
        jobRequestService.save(jobRequest);
        redirectAttributes.addFlashAttribute("msg", "Requisição confirmada!");
        status.setComplete();
        return "redirect:/requisicoes?passo=completo";

    }

}
