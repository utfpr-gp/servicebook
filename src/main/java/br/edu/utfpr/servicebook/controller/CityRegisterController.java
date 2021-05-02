package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.StateDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.mapper.CityMapper;
import br.edu.utfpr.servicebook.model.mapper.StateMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.StateService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("admin/city-register");
        List<State> states = stateService.findAll();


        List<StateDTO> stateDTOs = states.stream()
                .map(state -> stateMapper.toResponseDto(state))
                .collect(Collectors.toList());
        mv.addObject("states", stateDTOs);

        return mv;
    }

    @PostMapping
    public ModelAndView save(@Valid CityDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) throws IOException {
        for (FieldError e : errors.getFieldErrors()) {
            log.info(e.getField() + " -> " + e.getCode());
        }

        if (errors.hasErrors()) {
            return errorFowarding(dto, errors);
        }

        Optional<State> state = stateService.findById(dto.getIdState());

        if (state.isPresent()) {
            Optional<City> cityIsExist = cityService.findByNameAndState(dto.getName(), state.get());

            if (!cityIsExist.isPresent()) {

                if(!dto.getImage().isEmpty()){
                    File city_image = Files.createTempFile("temp", dto.getImage().getOriginalFilename()).toFile();
                    dto.getImage().transferTo(city_image);
                    Map data = cloudinary.uploader().upload(city_image, ObjectUtils.asMap("folder", "cities"));

                    City city = cityMapper.toEntity(dto);
                    city.setState(state.get());
                    city.setImage((String)data.get("url"));
                    cityService.save(city);

                    redirectAttributes.addFlashAttribute("msg", "Cidade cadastrada com sucesso!");
                }else{
                    errors.rejectValue("image", "error.dto", "Imagem é obrigatório!");
                    return errorFowarding(dto, errors);
                }

            } else {
                errors.rejectValue("name", "error.dto", "A cidade já está cadastrada.");
                return errorFowarding(dto, errors);
            }
        } else {
            errors.rejectValue("idState", "error.dto", "Estado inválido...!");
            return errorFowarding(dto, errors);
        }

        return new ModelAndView("redirect:cidades");
    }

    public ModelAndView errorFowarding(CityDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/city-register");

        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        List<State> states = stateService.findAll();
        List<StateDTO> stateDTOs = states.stream()
                .map(st -> stateMapper.toResponseDto(st))
                .collect(Collectors.toList());

        mv.addObject("states", stateDTOs);

        return mv;
    }

}

