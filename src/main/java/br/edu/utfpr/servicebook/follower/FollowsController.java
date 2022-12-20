package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.controller.ExpertiseController;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobCandidateMapper;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelIndividualDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/follow")
@Controller
public class FollowsController {

    public static final Logger log = LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    FollowsService followsService;

    @Autowired
    FollowsRepository followsRepository;

    @Autowired
    FollowsMapper followsMapper;

    @Autowired
    JobCandidateService jobCandidateService;

    @Autowired
    JobCandidateMapper jobCandidateMapper;

    /**
     * Trata da solitação via Fetch API para um cliente seguir um profissional.
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> save(@Valid FollowsDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) {

        String currentUserEmail = CurrentUserUtil.getCurrentUserEmail();

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        Follows follows = new Follows(dto.getClient(), dto.getProfessional());
        followsService.save(follows);

        return ResponseEntity.ok().build();
    }

    /**
     * Remove o registro de seguir de um cliente para um profissional
     * @param professionalId
     * @param redirectAttributes
     * @return
     */
    @DeleteMapping("/professional/{professionalId}")
    public ResponseEntity<Void> delete(@PathVariable Long professionalId, RedirectAttributes redirectAttributes) {

        String currentUserEmail = CurrentUserUtil.getCurrentUserEmail();

        Optional<Individual> oClient = individualService.findByEmail(currentUserEmail);
        Optional<Individual> oProfessional = individualService.findById(professionalId);

        if(!oProfessional.isPresent() || !oClient.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        List<Follows> followList = followsService.findFollowProfessionalClient(oProfessional.get(), oClient.get());

        if(followList != null && !followList.isEmpty()){
            followsService.delete(followList.get(0));
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/showfollow")
    public ModelAndView showDetailsFollows() throws Exception {
        ModelAndView mv = new ModelAndView("client/details-follows");
        System.err.println("entrou na rota follows listagem.....");
        Optional<Individual> oindividual = individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail());
        IndividualDTO clientDTO = individualMapper.toDto(oindividual.get());
        SidePanelIndividualDTO sidePanelIndividualDTO = SidePanelUtil.getSidePanelDTO(clientDTO);
        mv.addObject("user", sidePanelIndividualDTO);

        //lista de seguidores
        List<Follows> followsList = followsService.findFollowingByClient(oindividual.get());

        List<Individual> oindiviList = followsList.stream()
                .map(follows -> {
                    return follows.getProfessional();
                })
                .collect(Collectors.toList());

//        List<JobCandidateDTO> jobCandidatesDTOs = oindiviList.stream()
//                .map(individual -> {
//                    return  jobCandidateMapper.toDto(jobCandidateService.findByIndividual(individual));
//
//
//                })
//                .collect(Collectors.toList());


//        List<JobCandidateDTO> jobCandidatesDTOs = jobCandidates.stream()
//                .map(candidate -> {
//                    Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(candidate.getIndividual());
//                    candidate.getIndividual().setFollowsAmount(oProfessionalFollowingAmount.get());
//                    return jobCandidateMapper.toDto(candidate);
//                })
//                .collect(Collectors.toList());


        mv.addObject("follows", oindiviList);

//        followsDTOList.get().getProfessional().getName();

        boolean isClient = true;
        mv.addObject("isClient", isClient);

        return mv;
    }


}
