package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;

import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.SneakyThrows;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private Cloudinary cloudinary;

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

    /**
     * Persiste uma especialidade com ícone .svg.
     * Caso não haja tal especialidade no BD, ele persiste.
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     */
    @PostMapping
    public ModelAndView save(@Valid ExpertiseDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        if(!isValidateImage(dto.getIcon())) {
            errors.rejectValue("icon", "dto.icon", "Por favor, envie um ícone no formato SVG.");
            return errorFowarding(dto, errors);
        }


        Optional<Expertise> oExpertise = expertiseService.findByName(dto.getName());
        if (oExpertise.isPresent()) {
            errors.rejectValue("name", "error.dto", "A especialidade já está cadastrada!");
            return errorFowarding(dto, errors);
        }

        Map data = null;
        try {
            File jobImage = Files.createTempFile("temp", dto.getIcon().getOriginalFilename()).toFile();
            dto.getIcon().transferTo(jobImage);
            data = cloudinary.uploader().upload(jobImage, ObjectUtils.asMap("folder", "images"));

        }catch (IOException exception) {
            errors.rejectValue("name", "error.dto", "Houve um erro ao manipular o ícone.");
            return errorFowarding(dto, errors);
        }
        dto.setPathIcon(data != null ? (String) data.get("url") : oExpertise.get().getPathIcon());
        Expertise expertise = expertiseMapper.toEntity(dto);
        expertiseService.save(expertise);

        redirectAttributes.addFlashAttribute("msg", "Profissão salva com sucesso!");

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

        Optional<Expertise> oExpertise = expertiseService.findById(id);

        if(!oExpertise.isPresent()){
            throw new EntityNotFoundException("A especialidade não foi encontrada!");
        }

        String icon = oExpertise.get().getPathIcon();
        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(oExpertise.get());
        mv.addObject("dto", expertiseDTO);
        mv.addObject("icon", icon);

        String[] urlExplode = icon.split("/");
        String idIcon = urlExplode[urlExplode.length-1];

        mv.addObject("idIcon", idIcon);

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
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        log.debug("Removendo uma profissão com id {}", id);
        Optional <Expertise> optionalProfession = this.expertiseService.findById(id);
        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(optionalProfession.get());

        if(!optionalProfession.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        try{
            this.expertiseService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Profissão removida com sucesso!");
            return "redirect:/especialidades";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "Profissão não pode ser removida pois já esta sendo utilizada por profissionais!");
            return "redirect:/especialidades";
        }
    }

    public ModelAndView errorFowarding(ExpertiseDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/profession-registration");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        return mv;
    }

    public boolean isValidateImage(MultipartFile image){
        List<String> contentTypes = Arrays.asList("image/svg");

        for(int i = 0; i < contentTypes.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(contentTypes.get(i))){
                return true;
            }
        }

        return false;
    }
}