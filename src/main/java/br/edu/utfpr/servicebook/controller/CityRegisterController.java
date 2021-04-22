package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.StateDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.mapper.CityMapper;
import br.edu.utfpr.servicebook.model.mapper.StateMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RequestMapping("/cidades")
@Controller
public class CityRegisterController {

    public static final Logger log =
            LoggerFactory.getLogger(CityRegisterController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private StateService stateService;

    @Autowired
    private StateMapper stateMapper;

    @GetMapping
    public ModelAndView showForm(){
        ModelAndView mv = new ModelAndView("city-register");
        List<State> states = stateService.findAll();

        List<StateDTO> stateDTOs = states.stream()
                .map(state -> stateMapper.toResponseDto(state))
                .collect(Collectors.toList());
        mv.addObject("states", stateDTOs);

        return mv;
    }


    @PostMapping
    public ModelAndView save(@Valid CityDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            ModelAndView mv = new ModelAndView("city-register");
            mv.addObject("dto", dto);
            mv.addObject("errors", errors.getAllErrors());

            List<State> states = stateService.findAll();
            List<StateDTO> stateDTOs = states.stream()
                    .map(state -> stateMapper.toResponseDto(state))
                    .collect(Collectors.toList());

            mv.addObject("states", stateDTOs);

            return mv;
        }

        City city = cityMapper.toEntity(dto);
        cityService.save(city);


        redirectAttributes.addFlashAttribute("msg", "Cidade cadastrada com sucesso!");
        return new ModelAndView("redirect:cidades");
    }
}

