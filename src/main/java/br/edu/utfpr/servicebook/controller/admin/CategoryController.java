package br.edu.utfpr.servicebook.controller.admin;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.CategoryDTO;
import br.edu.utfpr.servicebook.model.entity.Category;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.mapper.CategoryMapper;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.CategoryService;
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

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/a/categorias")
@Controller
public class CategoryController {
    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showForm(HttpServletRequest request,
                                 @RequestParam(value = "pag", defaultValue = "1") int page,
                                 @RequestParam(value = "siz", defaultValue = "5") int size,
                                 @RequestParam(value = "ord", defaultValue = "name") String order,
                                 @RequestParam(value = "dir", defaultValue = "ASC") String direction){

        ModelAndView mv = new ModelAndView("admin/category");

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<Category> categoryPage = categoryService.findAll(pageRequest);

        List<CategoryDTO> categoryDTOS = categoryPage.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("categories", categoryDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(categoryPage);
        mv.addObject("pagination", paginationDTO);
        return mv;
    }

    /**
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     */
    @PostMapping
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView save(@Valid CategoryDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        // Se o id for nulo, é uma inserção
        if(dto.getId() == null){
            Optional<Category> oCategory = categoryService.findByName(dto.getName());
            if (oCategory.isPresent()) {
                errors.rejectValue("name", "error.dto", "A Categoria já está cadastrada!");
                return errorFowarding(dto, errors);
            }
        }

        // Se o id não for nulo, é uma atualização
        if(dto.getId() != null){
            Optional<Category> oExistingCategory = categoryService.findById(dto.getId());

            if (!oExistingCategory.isPresent()) {
                throw new EntityNotFoundException("A categoria não foi encontrada!");
            }

            Category category = oExistingCategory.get();
            Optional<Category> oOtherCategory = categoryService.findByName(dto.getName());
            if (oOtherCategory.isPresent()) {
                if(category.getId() != oOtherCategory.get().getId()) {
                    errors.rejectValue("name", "error.dto", "Não é possível atualizar. A categoria já está cadastrada!");
                    return errorFowarding(dto, errors);
                }
            }
        }

        Category expertise = categoryMapper.toEntity(dto);
        categoryService.save(expertise);

        redirectAttributes.addFlashAttribute("msg", "Categoria salva com sucesso!");

        return new ModelAndView("redirect:/a/categorias");
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

        ModelAndView mv = new ModelAndView("admin/category");

        if(id < 0){
            throw new InvalidParamsException("O identificador não pode ser negativo.");
        }

        Optional<Category> oCategory = categoryService.findById(id);

        if(!oCategory.isPresent()){
            throw new EntityNotFoundException("A especialidade não foi encontrada!");
        }

        CategoryDTO categoryDTO = categoryMapper.toDto(oCategory.get());
        mv.addObject("dto", categoryDTO);


        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);
        Page<Category> categoryPage = categoryService.findAll(pageRequest);

        List<CategoryDTO> categoryDTOS = categoryPage.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("categories", categoryDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(categoryPage, "/a/categorias/" + id);
        mv.addObject("pagination", paginationDTO);
        return mv;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        log.debug("Removendo uma categoria com id {}", id);
        Optional <Category> optionalCategory = this.categoryService.findById(id);
        CategoryDTO categoryDTO = categoryMapper.toDto(optionalCategory.get());

        if(!optionalCategory.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        try{
            this.categoryService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Categoria removida com sucesso!");
            return "redirect:/a/categorias";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "Categoria não pode ser removida pois já esta sendo utilizada por uma especialidade!");
            return "redirect:/a/categorias";
        }
    }

    private ModelAndView errorFowarding(CategoryDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/category");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Category> categoryPage = categoryService.findAll(pageRequest);

        List<CategoryDTO> categoryDTOS = categoryPage.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("categories", categoryDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(categoryPage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }
}
