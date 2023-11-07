package br.edu.utfpr.servicebook.controller.admin;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.CityDTO2;
import br.edu.utfpr.servicebook.model.dto.StateDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Expertise;
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

@RequestMapping("/a/cidades")
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

    @Autowired
    private PaginationUtil paginationUtil;

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

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(cityPage);
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

        Optional<State> oState = stateService.findById(dto.getIdState());
        if(!oState.isPresent()){
            throw new EntityNotFoundException("O estado não foi encontrado!");
        }

        // Se o id for nulo, significa que é o cadastro de uma nova cidade
        if(dto.getId() == null){
            // Lógica para cadastro de uma nova cidade, pode existir cidade com mesmo nome mas em estados diferentes
            Optional<City> oCity = cityService.findByNameAndState(dto.getName(), oState.get());

            if(oCity.isPresent()){
                errors.rejectValue("name", "error.dto", "A cidade já está cadastrada.");
                return errorFowarding(dto, errors);
            }

            if(!isValidateImage(dto.getImage())) {
                errors.rejectValue("image", "dto.image", "Por favor, envie uma imagem válida.");
                return errorFowarding(dto, errors);
            }

            String url = null;
            try{
                url = uploadImage(dto);
            }
            catch (IOException e){
                errors.rejectValue("image", "error.dto", "Houve um erro ao persistir a imagem.");
                return errorFowarding(dto, errors);
            }
            dto.setPathImage(url);
        }

        // Se o id não for nulo, significa que é a atualização de uma cidade existente
        if(dto.getId() != null){
            // Lógica para atualização de uma cidade existente
            Optional<City> oExistingCity = cityService.findById(dto.getId());

            if (!oExistingCity.isPresent()) {
                throw new EntityNotFoundException("A cidade não foi encontrada!");
            }

            //verifica se o usuário mudou o nome para uma cidade existente
            City city = oExistingCity.get();
            Optional<City> oOtherCity = cityService.findByNameAndState(dto.getName(), oState.get());
            if (oOtherCity.isPresent()) {
                if(city.getId() != oOtherCity.get().getId()) {
                    errors.rejectValue("name", "error.dto", "A cidade já está cadastrada!");
                    return errorFowarding(dto, errors);
                }
            }

            //verifica se o usuário mudou a imagem
            String url = city.getPathImage();
            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                //verifica se o ícone é válido
                if(!isValidateImage(dto.getImage())) {
                    errors.rejectValue("icon", "dto.image", "Por favor, envie uma imagem no formato PNG or JPG.");
                    return errorFowarding(dto, errors);
                }

                //deleta a imagem antiga
                deleteImage(city.getPathImage());

                //insere a imagem no cloudinary
                try {
                    url = uploadImage(dto);
                }catch (IOException exception) {
                    errors.rejectValue("name", "error.dto", "Houve um erro ao manipular a imagem.");
                    return errorFowarding(dto, errors);
                }
            }
            //se a imagem não foi atualizado, coloca a imagem existente
            dto.setPathImage(url);
        }

        City city = cityMapper.toEntity(dto);
        city.setState(oState.get());
        cityService.save(city);

        redirectAttributes.addFlashAttribute("msg", "Cidade cadastrada com sucesso!");

        return new ModelAndView("redirect:/a/cidades");
    }

    /**
     * Apresenta o formulário de atualização de cidade
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showFormUpdate(@PathVariable("id") Long id) throws Exception {

        ModelAndView mv = new ModelAndView("admin/city-register");

        if(id < 0){
            throw new InvalidParameterException("O identificador não pode ser negativo.");
        }

        Optional<City> oCity = cityService.findById(id);

        if(!oCity.isPresent()){
            throw new EntityNotFoundException("A cidade não foi encontrada!");
        }

        CityDTO cityDTO = cityMapper.toResponseDto(oCity.get());
        mv.addObject("dto", cityDTO);

        mv.addObject("states", listStateDTO());

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.valueOf("ASC"), "state");

        Page<City> cityPage = cityService.findAll(pageRequest);

        mv.addObject("cities", listCityDTO(cityPage));

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(cityPage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    /**
     * Exclui a cidade
     * @param id Id da cidade
     * @throws IOException
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional<City> oCity = cityService.findById(id);

        if(!oCity.isPresent()){
            throw new EntityNotFoundException("A cidade não foi encontrada!");
        }

        deleteImage(oCity.get().getPathImage());
        cityService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "A cidade foi removida com sucesso!");

        return "redirect:/a/cidades";
    }

    private boolean isValidateImage(MultipartFile image){
        List<String> contentTypes = Arrays.asList("image/png", "image/jpg", "image/jpeg");

        for(int i = 0; i < contentTypes.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(contentTypes.get(i))){
                return true;
            }
        }

        return false;
    }

    private String recoverIdImage(String urlImage){
        String[] urlExplode = urlImage.split("/");
        String fileName = urlExplode[urlExplode.length-1];
        String[] fileNameExplode = fileName.split("\\.");
        String idImage = fileNameExplode[0];

        return idImage;
    }

    /**
     * Exclui a imagem da cidade
     * @throws IOException
     */
    private void deleteImage(String urlImage) throws IOException {
        cloudinary.uploader().destroy("cities/"+recoverIdImage(urlImage), ObjectUtils.emptyMap());
    }

    /**
     * Faz o upload da imagem da cidade
     * @param dto
     * @throws IOException
     */
    public String uploadImage(CityDTO dto) throws IOException {

        File cityImage = Files.createTempFile("temp", dto.getImage().getOriginalFilename()).toFile();
        dto.getImage().transferTo(cityImage);
        Map data = cloudinary.uploader().upload(cityImage, ObjectUtils.asMap("folder", "cities"));

        return (String)data.get("url");
    }

    /**
     * Faz o encaminhamento para mostrar as mensagens de erro.     *
     * @return
     */
    private ModelAndView errorFowarding(CityDTO dto,BindingResult errors) {

        ModelAndView mv = new ModelAndView("admin/city-register");

        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        mv.addObject("states", listStateDTO());

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.valueOf("ASC"), "state");
        Page<City> cityPage = cityService.findAll(pageRequest);

        mv.addObject("cities", listCityDTO(cityPage));

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(cityPage);
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

