package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ProfessionDTO;
import br.edu.utfpr.servicebook.model.entity.Profession;
import br.edu.utfpr.servicebook.model.mapper.ProfessionMapper;
import br.edu.utfpr.servicebook.service.ProfessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/profissao")
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
    public String save(@Validated ProfessionDTO dto, Errors errors, RedirectAttributes redirectAttributes){
        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            ModelAndView mv = new ModelAndView();
            //salva o DTO para manter tais dados em caso de erro
            mv.addObject("dto", dto);
            //salva os erros para serem apresentados
            mv.addObject("errors", errors.getAllErrors());
            //encaminhamento para a visão
            return "admin/profession-registration";
        }

        Profession profession = professionMapper.toEntity(dto);
        professionService.save(profession);
        redirectAttributes.addFlashAttribute("msg", "Profissão salva com sucesso!");
        return "redirect:/profissao";
    }
}
