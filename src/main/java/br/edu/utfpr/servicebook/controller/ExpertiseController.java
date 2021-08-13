package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;

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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/especialidades")
@Controller
public class ExpertiseController {

    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @GetMapping
    public ModelAndView showForm(HttpServletRequest request,
                                 @RequestParam(value = "pag", defaultValue = "1") int page,
                                 @RequestParam(value = "siz", defaultValue = "5") int size,
                                 @RequestParam(value = "ord", defaultValue = "name") String order,
                                 @RequestParam(value = "dir", defaultValue = "ASC") String direction){
        ModelAndView mv = new ModelAndView("admin/profession-registration");
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<Expertise> expertisePage = expertiseService.findAll(pageRequest);

        List<ExpertiseDTO> professionDTOs = expertisePage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("professions", professionDTOs);

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(expertisePage);
        mv.addObject("pagination", paginationDTO);
        return mv;
    }

    @PostMapping
    public ModelAndView save(@Valid ExpertiseDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){
        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        Optional<Expertise> optionalProfession = expertiseService.findByName(dto.getName());

        if (!optionalProfession.isPresent()) {
            Expertise profession = expertiseMapper.toEntity(dto);
            expertiseService.save(profession);
            List<Expertise> professions = expertiseService.findAll();
            List<ExpertiseDTO> professionDTOs = professions.stream()
                    .map(s -> expertiseMapper.toDto(s))
                    .collect(Collectors.toList());
            redirectAttributes.addFlashAttribute("msg", "Profissão salva com sucesso!");
        } else {
            errors.rejectValue("name", "error.dto", "A profissão já está cadastrada.");
            return errorFowarding(dto, errors);
        }
        return new ModelAndView("redirect:especialidades");
    }

    @GetMapping("/{id}")
    public ModelAndView showFormForUpdate(@PathVariable("id") Long id, HttpServletRequest request,
                                          @RequestParam(value = "pag", defaultValue = "1") int page,
                                          @RequestParam(value = "siz", defaultValue = "4") int size,
                                          @RequestParam(value = "ord", defaultValue = "name") String order,
                                          @RequestParam(value = "dir", defaultValue = "ASC") String direction){
        ModelAndView mv = new ModelAndView("admin/profession-registration");

        if(id < 0){
            throw new InvalidParamsException("O identificador não pode ser negativo.");
        }

        Optional<Expertise> optionalProfession = expertiseService.findById(id);

        if(!optionalProfession.isPresent()){
            throw new EntityNotFoundException("O aluno não foi encontrado pelo id informado.");
        }
        ExpertiseDTO professionDTO = expertiseMapper.toDto(optionalProfession.get());
        mv.addObject("dto", professionDTO);

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<Expertise> professionPage = expertiseService.findAll(pageRequest);

        List<ExpertiseDTO> professionDTOs = professionPage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("professions", professionDTOs);

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(professionPage, "/especialidades/" + id);
        mv.addObject("pagination", paginationDTO);
        return mv;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.debug("Removendo uma profissão com id {}", id);
        Optional <Expertise> optionalProfession = this.expertiseService.findById(id);

        if(!optionalProfession.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        this.expertiseService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Profissão removida com sucesso!");
        return "redirect:/especialidades";
    }

    public ModelAndView errorFowarding(ExpertiseDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/profession-registration");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        return mv;
    }
}