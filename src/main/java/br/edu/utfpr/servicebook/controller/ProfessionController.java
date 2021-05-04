package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ProfessionDTO;
import br.edu.utfpr.servicebook.model.entity.Profession;
import br.edu.utfpr.servicebook.model.mapper.ProfessionMapper;
import br.edu.utfpr.servicebook.service.ProfessionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/profissoes")
@Controller
public class ProfessionController {

    public static final Logger log =
            LoggerFactory.getLogger(ProfessionController.class);

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private ProfessionMapper professionMapper;

    @GetMapping
    public String show(){
        return "admin/profession-registration";
    }

    @PostMapping
    public ModelAndView save(@Valid ProfessionDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){
        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        Optional<Profession> optionalProfession = professionService.findByName(dto.getName());

        if (!optionalProfession.isPresent()) {
            Profession profession = professionMapper.toEntity(dto);
            professionService.save(profession);
            redirectAttributes.addFlashAttribute("msg", "Profissão salva com sucesso!");
        } else {
            errors.rejectValue("name", "error.dto", "A profissão já está cadastrada.");
            return errorFowarding(dto, errors);
        }
        return new ModelAndView("redirect:profissoes");
    }

    public ModelAndView errorFowarding(ProfessionDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/profession-registration");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        return mv;
    }
}