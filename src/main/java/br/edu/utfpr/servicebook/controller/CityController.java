package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.CityDTO2;
import br.edu.utfpr.servicebook.model.dto.StateDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.mapper.CityMapper;
import br.edu.utfpr.servicebook.model.mapper.StateMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.StateService;

import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/cidades")
@Controller
public class CityController {

    public static final Logger log =
            LoggerFactory.getLogger(CityController.class);

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

    @Autowired
    private IAuthentication authentication;

    @GetMapping
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showForm(HttpServletRequest request,
                                 @RequestParam(value = "pag", defaultValue = "1") int page,
                                 @RequestParam(value = "siz", defaultValue = "3") int size,
                                 @RequestParam(value = "ord", defaultValue = "state")String order,
                                 @RequestParam(value = "dir", defaultValue = "ASC") String direction) {

        ModelAndView mv = new ModelAndView("admin/city-register");

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<City> cityPage = cityService.findAll(pageRequest);

        mv.addObject("states", listStateDTO());
        mv.addObject("cities", listCityDTO(cityPage));

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(cityPage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    @PostMapping
    @RolesAllowed({RoleType.ADMIN})
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
                        File cityImage = Files.createTempFile("temp", dto.getImage().getOriginalFilename()).toFile();
                        dto.getImage().transferTo(cityImage);
                        Map data = cloudinary.uploader().upload(cityImage, ObjectUtils.asMap("folder", "cities"));

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
                        String urlImage = city.get().getImage();

                        City ct = cityMapper.toEntity(dto);
                        ct.setState(state.get());
                        ct.setName(dto.getName());
                        ct.setImage(urlImage);
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
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showFormUpdate(@PathVariable("id") Long id) throws Exception {

        ModelAndView mv = new ModelAndView("admin/city-register");

        if(id < 0){
            throw new InvalidParameterException("O identificador não pode ser null");
        }

        Optional<City> city = cityService.findById(id);

        if(!city.isPresent()){
            throw new EntityNotFoundException("A cidade não foi encontrada pelo id informado");
        }

        String urlImage = city.get().getImage();
        mv.addObject("imageCurrent", urlImage);

        String[] urlExplode = urlImage.split("/");

        String id_image = urlExplode[urlExplode.length-1];
        mv.addObject("idImage", id_image);


        CityDTO cityDTO = cityMapper.toResponseDto(city.get());
        mv.addObject("dto", cityDTO);

        mv.addObject("states", listStateDTO());

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.valueOf("ASC"), "state");

        Page<City> cityPage = cityService.findAll(pageRequest);

        mv.addObject("cities", listCityDTO(cityPage));

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(cityPage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        Optional<City> city = cityService.findById(id);

        if(city.isPresent()){
            deleteImage(city);
            cityService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Cidade deletada!");

            return "redirect:/cidades";
        }

        throw new EntityNotFoundException("A cidade não foi encontrada pelo id informado");
    }

    public boolean isValidateImage(MultipartFile image){
        List<String> contentTypes = Arrays.asList("image/png", "image/jpg", "image/jpeg");

        for(int i = 0; i < contentTypes.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(contentTypes.get(i))){
                return true;
            }
        }

        return false;
    }

    public String recoverIdImage(String urlImage){
        String[] urlExplode = urlImage.split("/");
        String fileName = urlExplode[urlExplode.length-1];
        String[] fileNameExplode = fileName.split("\\.");
        String idImage = fileNameExplode[0];

        return idImage;
    }

    public void deleteImage(Optional<City> city) throws IOException {
        String urlImage = city.get().getImage();
        cloudinary.uploader().destroy("cities/"+recoverIdImage(urlImage), ObjectUtils.emptyMap());
    }

    public void uploadImage(CityDTO dto) throws IOException {
        Optional<City> city = cityService.findById(dto.getId());

        File cityImage = Files.createTempFile("temp", dto.getImage().getOriginalFilename()).toFile();
        dto.getImage().transferTo(cityImage);
        Map data = cloudinary.uploader().upload(cityImage, ObjectUtils.asMap("folder", "cities"));

        City ct = cityMapper.toEntity(dto);
        ct.setImage((String)data.get("url"));
        cityService.save(ct);
    }

    public ModelAndView errorFowarding(CityDTO dto,BindingResult errors) {

        ModelAndView mv = new ModelAndView("admin/city-register");

        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        mv.addObject("states", listStateDTO());

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.valueOf("ASC"), "state");
        Page<City> cityPage = cityService.findAll(pageRequest);

        mv.addObject("cities", listCityDTO(cityPage));

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(cityPage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    private List<StateDTO> listStateDTO(){
        List<State> states = stateService.findAll();

        List<StateDTO> stateDTOs = states.stream()
                .map(st -> stateMapper.toResponseDto(st))
                .collect(Collectors.toList());

        return stateDTOs;
    }

    private List<CityDTO2> listCityDTO(Page<City> cityPage){

        List<CityDTO2> cityDTOs = cityPage.stream()
                .map(city -> cityMapper.toResponseDetail(city))
                .collect(Collectors.toList());

        return cityDTOs;
    }

}

