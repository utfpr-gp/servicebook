package br.edu.utfpr.servicebook.controller.admin;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.CategoryDTO;
import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Category;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.mapper.CategoryMapper;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import br.edu.utfpr.servicebook.util.TemplateUtil;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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

import javax.annotation.security.RolesAllowed;
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

@RequestMapping("/a/especialidades")
@Controller
public class ExpertiseController {

    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showForm(HttpServletRequest request,
                                 @RequestParam(value = "pag", defaultValue = "1") int page,
                                 @RequestParam(value = "siz", defaultValue = "5") int size,
                                 @RequestParam(value = "ord", defaultValue = "name") String order,
                                 @RequestParam(value = "dir", defaultValue = "ASC") String direction){

        ModelAndView mv = new ModelAndView("admin/expertise-register");

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<Expertise> expertisePage = expertiseService.findAll(pageRequest);

        List<ExpertiseDTO> expertiseDTOs = expertisePage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(expertisePage);
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
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView save(@Valid ExpertiseDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        //cadastro
        if(dto.getId() == null){
            if(!isValidateImage(dto.getIcon())) {
                errors.rejectValue("icon", "dto.icon", "Por favor, envie um ícone no formato SVG.");
                return errorFowarding(dto, errors);
            }

            Optional<Expertise> oExpertise = expertiseService.findByName(dto.getName());
            if (oExpertise.isPresent()) {
                errors.rejectValue("name", "error.dto", "A especialidade já está cadastrada!");
                return errorFowarding(dto, errors);
            }

            String url = null;
            try {
                url = uploadImage(dto);

            }catch (IOException exception) {
                errors.rejectValue("name", "error.dto", "Houve um erro ao manipular o ícone.");
                return errorFowarding(dto, errors);
            }
            dto.setPathIcon(url);
        }

        //atualização
        if(dto.getId() != null){
            // Lógica para atualização de uma expertise existente
            Optional<Expertise> oExistingExpertise = expertiseService.findById(dto.getId());

            if (!oExistingExpertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada!");
            }

            // Atualize as propriedades necessárias da expertise existente com base nos dados do DTO
            Expertise expertise = oExistingExpertise.get();

            //verifica se o usuário mudou o nome para uma especialidade existente
            Optional<Expertise> otherExpertise = expertiseService.findByName(dto.getName());
            if (otherExpertise.isPresent()) {
                if(expertise.getId() != otherExpertise.get().getId()) {
                    errors.rejectValue("name", "error.dto", "A especialidade já está cadastrada!");
                    return errorFowarding(dto, errors);
                }
            }

            //verifica se o usuário mudou o ícone
            String url = null;
            if (dto.getIcon() != null && !dto.getIcon().isEmpty()) {
                //verifica se o ícone é válido (formato .svg)
                if(!isValidateImage(dto.getIcon())) {
                    errors.rejectValue("icon", "dto.icon", "Por favor, envie um ícone no formato SVG.");
                    return errorFowarding(dto, errors);
                }

                //insere o ícone no cloudinary
                try {
                    url = uploadImage(dto);

                }catch (IOException exception) {
                    errors.rejectValue("name", "error.dto", "Houve um erro ao manipular o ícone.");
                    return errorFowarding(dto, errors);
                }
            }
            //se o ícone não foi atualizado, coloca a imagem existente
            dto.setPathIcon(url != null ? url : expertise.getPathIcon());
        }

        // Salve a expertise atualizada
        Expertise expertise = expertiseMapper.toEntity(dto);
        expertiseService.save(expertise);
        redirectAttributes.addFlashAttribute("msg", "A especialidade foi salva com sucesso!");
        return new ModelAndView("redirect:/a/especialidades");
    }

    /**
     * Mostra o formulário para atualizar uma especialidade.
     * @param id
     * @param request
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showFormForUpdate(@PathVariable("id") Long id, HttpServletRequest request,
                                          @RequestParam(value = "pag", defaultValue = "1") int page,
                                          @RequestParam(value = "siz", defaultValue = "4") int size,
                                          @RequestParam(value = "ord", defaultValue = "name") String order,
                                          @RequestParam(value = "dir", defaultValue = "ASC") String direction){

        ModelAndView mv = new ModelAndView("admin/expertise-register");

        if(id < 0){
            throw new InvalidParamsException("O identificador não pode ser negativo.");
        }

        Optional<Expertise> oExpertise = expertiseService.findById(id);

        if(!oExpertise.isPresent()){
            throw new EntityNotFoundException("A especialidade não foi encontrada!");
        }

        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(oExpertise.get());
        mv.addObject("dto", expertiseDTO);

//        String icon = oExpertise.get().getPathIcon();
//        String[] urlExplode = icon.split("/");
//        String idIcon = urlExplode[urlExplode.length-1];
//
//        mv.addObject("idIcon", idIcon);

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<Expertise> professionPage = expertiseService.findAll(pageRequest);

        List<ExpertiseDTO> expertisesDTOs = professionPage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertisesDTOs);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(professionPage, "/a/especialidades/" + id);
        mv.addObject("pagination", paginationDTO);
        return mv;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        log.debug("Removendo uma especialidade com id {}", id);
        Optional <Expertise> optionalProfession = this.expertiseService.findById(id);
        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(optionalProfession.get());

        if(!optionalProfession.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        try{
            this.expertiseService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Profissão removida com sucesso!");
            return "redirect:/a/especialidades";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "Profissão não pode ser removida pois já esta sendo utilizada por profissionais!");
            return "redirect:/a/especialidades";
        }
    }

    private ModelAndView errorFowarding(ExpertiseDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/expertise-register");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Expertise> expertisePage = expertiseService.findAll(pageRequest);

        List<ExpertiseDTO> expertiseDTOs = expertisePage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(expertisePage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    private boolean isValidateImage(MultipartFile image){
        List<String> contentTypes = Arrays.asList("image/svg");

        for(int i = 0; i < contentTypes.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(contentTypes.get(i))){
                return true;
            }
        }

        return false;
    }

    /**
     * Faz o upload da imagem para o cloudinary
     * @param dto
     * @return
     * @throws IOException
     */
    public String uploadImage(ExpertiseDTO dto) throws IOException {
        File image = Files.createTempFile("temp", dto.getIcon().getOriginalFilename()).toFile();
        dto.getIcon().transferTo(image);
        Map data = cloudinary.uploader().upload(image, ObjectUtils.asMap("folder", "images"));

        return (String)data.get("url");
    }
}