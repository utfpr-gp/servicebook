package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.controller.ExpertiseController;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelIndividualDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
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

    @PostMapping("/subscribe")
    public ModelAndView save(@Valid FollowsDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) {

        String currentUserEmail = CurrentUserUtil.getCurrentUserEmail();

        System.err.println("CONTROLLER FOLLOWA SAVE... DTO.CLIENT.." + dto.getClient());
        System.err.println("CONTROLLER FOLLOWA SAVE... DTO.PROFESSIONAL.." + dto.getProfessional());

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        Follows follows = new Follows(dto.getClient(), dto.getProfessional());
        followsService.save(follows);

        //fazer validação

        redirectAttributes.addFlashAttribute("msg", "Está seguindo!");
        return new ModelAndView("redirect:/minha-conta/profissional");
    }

    @GetMapping("/list")
    public ModelAndView showDetailsFollows() throws Exception {
        ModelAndView mv = new ModelAndView("client/details-follows");

        Optional<Individual> oindividual = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));

        System.err.println("entrou na rota follows listagem.....");
        System.err.println("client id.... " + oindividual.get().getId());

        IndividualDTO clientDTO = individualMapper.toDto(oindividual.get());
        SidePanelIndividualDTO sidePanelIndividualDTO = SidePanelUtil.getSidePanelDTO(clientDTO);
        mv.addObject("user", sidePanelIndividualDTO);

        Individual individual = individualMapper.optionalToEntity(oindividual);

        List<Follows> followsList = followsService.findFollowsByClient(individual);
//        List<Follows> followsList = followsService.findFollowsByProfessional(individual);

        List<FollowsDTO> followsDTOList = followsList.stream()
                .map(teste -> followsMapper.toDto(teste))
                .collect(Collectors.toList());

        mv.addObject("follows", followsDTOList);
//        followsDTOList.get().getProfessional().getName();

        boolean isClient = true;
        mv.addObject("isClient", isClient);

        return mv;
    }
    public ModelAndView errorFowarding(@Valid FollowsDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("/minha-conta/profissional");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        return mv;
    }

}
