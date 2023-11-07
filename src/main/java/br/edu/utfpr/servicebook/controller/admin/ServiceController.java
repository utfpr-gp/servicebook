package br.edu.utfpr.servicebook.controller.admin;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.model.repository.CompanyRepository;
import br.edu.utfpr.servicebook.model.repository.JobContractedRepository;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.DateUtil;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
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

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.DateFormatter;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.DateFormat;
import java.text.spi.DateFormatProvider;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/a/servicos")
@Controller
public class ServiceController {
    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    @PermitAll
    public ModelAndView showForm(HttpServletRequest request,
                                 @RequestParam(value = "pag", defaultValue = "1") int page,
                                 @RequestParam(value = "siz", defaultValue = "5") int size,
                                 @RequestParam(value = "ord", defaultValue = "name") String order,
                                 @RequestParam(value = "dir", defaultValue = "ASC") String direction){

        ModelAndView mv = new ModelAndView("admin/service-register");

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("services", serviceDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(servicePage);
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
    public ModelAndView save(@Valid ServiceDTO dto, BindingResult errors, RedirectAttributes redirectAttributes){

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return errorFowarding(dto, errors);
        }

        Optional<Expertise> oExpertise = expertiseService.findById(dto.getExpertiseId());
        if(!oExpertise.isPresent()){
            throw new EntityNotFoundException("A especialidade não foi encontrada!");
        }

        //verifica se o id é nulo, se for, é um novo registro
        if(dto.getId() == null){

            if(!isValidateImage(dto.getIcon())) {
                errors.rejectValue("icon", "dto.icon", "Por favor, envie um ícone no formato SVG.");
                return errorFowarding(dto, errors);
            }

            //verifica se já existe um serviço com o mesmo nome e especialidade
            Optional<Service> oService = serviceService.findByNameAndExpertise(dto.getName(), oExpertise.get());
            if (oService.isPresent()) {
                errors.rejectValue("name", "error.dto", "O serviço já está cadastrado!");
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

        //verifica se o id é diferente de nulo, se for, é uma atualização
        if(dto.getId() != null){
            // Lógica para atualização de um serviço existente
            Optional<Service> oExistingService = serviceService.findById(dto.getId());

            if (!oExistingService.isPresent()) {
                throw new EntityNotFoundException("O serviço não foi encontrado!");
            }

            // Atualize as propriedades necessárias do serviço existente com base nos dados do DTO
            Service service = oExistingService.get();

            //verifica se o usuário mudou o nome para um serviço existente
            Optional<Service> otherService = serviceService.findByNameAndExpertise(dto.getName(), oExpertise.get());
            if (otherService.isPresent()) {
                if(service.getId() != otherService.get().getId()) {
                    errors.rejectValue("name", "error.dto", "O serviço já está cadastrado!");
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
            dto.setPathIcon(url != null ? url : service.getPathIcon());
        }

        Service service = serviceMapper.toEntity(dto);
        service.setExpertise(oExpertise.get());
        serviceService.save(service);

        redirectAttributes.addFlashAttribute("msg", "Serviço salvo com sucesso!");

        return new ModelAndView("redirect:/a/servicos");
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

        ModelAndView mv = new ModelAndView("admin/service-register");

        if(id < 0){
            throw new InvalidParamsException("O identificador não pode ser negativo.");
        }

        Optional<Service> oService = serviceService.findById(id);

        if(!oService.isPresent()){
            throw new EntityNotFoundException("O serviço não foi encontrado!");
        }

        ServiceDTO serviceDTO = serviceMapper.toDto(oService.get());
        mv.addObject("dto", serviceDTO);

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.Direction.valueOf(direction), order);

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("services", serviceDTOS);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(servicePage, "/servicos/" + id);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        log.debug("Removendo um serviço com id {}", id);
        Optional <Service> optionalService = this.serviceService.findById(id);
        ServiceDTO serviceDTO = serviceMapper.toDto(optionalService.get());

        if(!optionalService.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        try{
            this.serviceService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Serviço removido com sucesso!");
            return "redirect:/a/servicos";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "Serviço não pode ser removido pois já esta sendo utilizado!");
            return "redirect:/a/servicos";
        }
    }

    private ModelAndView errorFowarding(ServiceDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/service-register");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("services", serviceDTOS);

        //carrega a paginação
        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(servicePage);
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
    public String uploadImage(ServiceDTO dto) throws IOException {
        File image = Files.createTempFile("temp", dto.getIcon().getOriginalFilename()).toFile();
        dto.getIcon().transferTo(image);
        Map data = cloudinary.uploader().upload(image, ObjectUtils.asMap("folder", "images"));

        return (String)data.get("url");
    }
}
