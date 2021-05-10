package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.CityDTO2;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
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
        List<City> cities = cityService.findAll();

        List<StateDTO> stateDTOs = states.stream()
                .map(state -> stateMapper.toResponseDto(state))
                .collect(Collectors.toList());
        mv.addObject("states", stateDTOs);

        List<CityDTO2> cityDTOs = cities.stream()
                .map(city -> cityMapper.toResponseDetail(city))
                .collect(Collectors.toList());
        mv.addObject("cities", cityDTOs);

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

                    if(isValidateImage(dto.getImage())){
                        File city_image = Files.createTempFile("temp", dto.getImage().getOriginalFilename()).toFile();
                        dto.getImage().transferTo(city_image);
                        Map data = cloudinary.uploader().upload(city_image, ObjectUtils.asMap("folder", "cities"));

                        City city = cityMapper.toEntity(dto);
                        city.setState(state.get());
                        city.setImage((String)data.get("url"));
                        cityService.save(city);

                        redirectAttributes.addFlashAttribute("msg", "Cidade cadastrada com sucesso!");

                    }else{
                        errors.rejectValue("image", "error.dto", "O arquivo deve ser uma imagem (.jpg, .jpeg ou .png)");
                        return errorFowarding(dto, errors);
                    }
                }else {
                    if(dto.getId() != null){
                        Optional<City> city = cityService.findById(dto.getId());
                        String url_image = city.get().getImage();

                        City ct = cityMapper.toEntity(dto);
                        ct.setState(state.get());
                        ct.setName(dto.getName());
                        ct.setImage(url_image);
                        cityService.save(ct);

                        redirectAttributes.addFlashAttribute("msg", "Dados da cidade atualizados!");
                    }else{
                        errors.rejectValue("image", "error.dto", "Imagem é obrigatório!");
                        return errorFowarding(dto, errors);
                    }
                }
            } else {
                if(isValidateImage(dto.getImage())){
                    deleteImage(cityIsExist);
                    uploadImage(dto);

                    redirectAttributes.addFlashAttribute("msg", "Imagem atualizada!");
                }else{
                    errors.rejectValue("name", "error.dto", "A cidade já está cadastrada.");
                    return errorFowarding(dto, errors);
                }
            }
        } else {
            errors.rejectValue("idState", "error.dto", "Estado inválido...!");
            return errorFowarding(dto, errors);
        }

        return new ModelAndView("redirect:cidades");
    }

    @GetMapping("/{id}")
    public ModelAndView showFormUpdate(@PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("admin/city-register");

        if(id < 0){
            throw new InvalidParameterException("O identificador não pode ser null");
        }

        Optional<City> city = cityService.findById(id);

        if(!city.isPresent()){
            throw new EntityNotFoundException("A cidade não foi encontrada pelo id informado");
        }

        String url_image = city.get().getImage();
        mv.addObject("image_current", url_image);

        String[] url_explode = url_image.split("/");

        String id_image = url_explode[8];
        mv.addObject("id_image", id_image);


        CityDTO cityDTO = cityMapper.toResponseDto(city.get());
        mv.addObject("dto", cityDTO);

        List<State> states = stateService.findAll();
        List<StateDTO> stateDTOs = states.stream()
                .map(st -> stateMapper.toResponseDto(st))
                .collect(Collectors.toList());

        mv.addObject("states", stateDTOs);

        return mv;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        Optional<City> city = cityService.findById(id);

        deleteImage(city);
        cityService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Cidade deletada!");

        return "redirect:/cidades";

    }

    public boolean isValidateImage(MultipartFile image){
        List<String> content_types = Arrays.asList("image/png", "image/jpg", "image/jpeg");

        for(int i = 0; i < content_types.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(content_types.get(i))){
                return true;
            }
        }

        return false;
    }

    public String recoverIdImage(String url_image){
        String[] url_explode = url_image.split("/");
        String file_name = url_explode[8];
        String[] file_name_explode = file_name.split("\\.");
        String id_image = file_name_explode[0];

        return id_image;
    }

    public void deleteImage(Optional<City> city) throws IOException {
        String url_image = city.get().getImage();
        cloudinary.uploader().destroy("cities/"+recoverIdImage(url_image), ObjectUtils.emptyMap());
    }

    public void uploadImage(CityDTO dto) throws IOException {
        Optional<City> city = cityService.findById(dto.getId());

        File city_image = Files.createTempFile("temp", dto.getImage().getOriginalFilename()).toFile();
        dto.getImage().transferTo(city_image);
        Map data = cloudinary.uploader().upload(city_image, ObjectUtils.asMap("folder", "cities"));

        City ct = cityMapper.toEntity(dto);
        ct.setImage((String)data.get("url"));
        cityService.save(ct);
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

        List<City> cities = cityService.findAll();
        List<CityDTO2> cityDTOs = cities.stream()
                .map(city -> cityMapper.toResponseDetail(city))
                .collect(Collectors.toList());
        mv.addObject("cities", cityDTOs);

        return mv;
    }

}

