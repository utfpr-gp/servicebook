package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalExpertiseDTO2;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/profissionais")
@Controller
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ProfessionalExpertiseMapper professionalExpertiseMapper;

    @GetMapping
    public ModelAndView showProfessionalAccount() throws Exception {
        ModelAndView mv = new ModelAndView("professional/my-account");

        ProfessionalDTO professionalDTO = professionalMapper.toDto(this.getProfessional());
        mv.addObject("professional", professionalDTO);

        return mv;
    }

    @GetMapping("/especialidades")
    public ModelAndView showExpertises() throws Exception {
        ModelAndView mv = new ModelAndView("professional/my-expertises");

        Optional<List<Expertise>> oExpertises = Optional.ofNullable(expertiseService.findAll());
        if (!oExpertises.isPresent()) {
            throw new Exception("Nenhuma expecialidade encontrada");
        }

        List<ExpertiseDTO> expertisesDTOs = oExpertises.get().stream()
                                            .map(s -> expertiseMapper.toDto(s))
                                            .collect(Collectors.toList());

        Optional<List<ProfessionalExpertise>> oProfessionalExpertises = Optional.ofNullable(professionalExpertiseService.findByProfessional(this.getProfessional()));
        List<ProfessionalExpertiseDTO2> professionalExpertiseDTOs = oProfessionalExpertises.get().stream()
                                                                    .map(s -> professionalExpertiseMapper.toResponseDTO(s))
                                                                    .collect(Collectors.toList());

        ProfessionalDTO professionalDTO = professionalMapper.toDto(this.getProfessional());
        mv.addObject("professional", professionalDTO);
        mv.addObject("expertises", expertisesDTOs);
        mv.addObject("professionalExpertises", professionalExpertiseDTOs);

        return mv;
    }

    @PostMapping("/especialidades")
    public ModelAndView saveExpertises(@Valid ProfessionalExpertiseDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {
        int[] ids = this.stringToArray(dto.getIds());

        Professional professional = this.getProfessional();

        for (int id : ids) {
            Expertise e = expertiseService.findById((long) id).get();
            ProfessionalExpertise p = professionalExpertiseService.save(new ProfessionalExpertise(professional, e));
        }

        ModelAndView mv = new ModelAndView("redirect:especialidades");

        return mv;
    }

    @GetMapping("/editar")
    public ModelAndView showFormEditProfessional() {
        ModelAndView mv = new ModelAndView("professional/edit-account");

        return mv;
    }

    private Professional getProfessional() throws Exception {
        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Opss! Não foi possivel encontrar seus dados, tente fazer login novamente");
        }

        return oProfessional.get();
    }

    private int[] stringToArray(String ids) {
        if (ids == null) {
            int[] ints = {};
            return ints;
        }

        String[] idsString = ids.split(",");
        int[] ints = new int[idsString.length];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(idsString[i]);
        }

        return ints;
    }

}
