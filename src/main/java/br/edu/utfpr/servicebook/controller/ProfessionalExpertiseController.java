package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/profissional/especialidades")
@Controller
public class ProfessionalExpertiseController {

    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private IAuthentication authentication;

    /**
     * Apresenta a tela para o profissional adicionar especialidades.
     * @return
     * @throws Exception
     */
    @GetMapping()
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showExpertises()  throws Exception {

        User professional = this.getAuthenticatedUser();

        //lista de categorias
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        List<ExpertiseDTO> professionalExpertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        List<Expertise> professionPage = expertiseService.findExpertiseNotExist(getAuthenticatedUser().getId());

        List<ExpertiseDTO> otherExpertisesDTOs = professionPage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("professional/my-expertises");
        mv.addObject("categories", categoryDTOs);
        mv.addObject("otherExpertises", otherExpertisesDTOs);
        mv.addObject("professionalExpertises", professionalExpertiseDTOs);
        return mv;
    }

    /**
     * Apresenta a tela para o profissional adicionar especialidades.
     * Envia apenas as categorias. Assim, as especialidades disponíveis são carregadas via ajax.
     * @return
     * @throws Exception
     */
    @GetMapping("/novo")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showFormToRegister()  throws Exception {

        User professional = this.getAuthenticatedUser();

        //lista de categorias
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("professional/my-expertises-register");
        mv.addObject("categories", categoryDTOs);

        return mv;
    }

    /**
     * Retorna as especialidades de uma categoria para uma requisição ajax.
     * Porém, apenas as especialidades que o profissional não possui para a categoria.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/categorias/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    @ResponseBody
    public List<ExpertiseDTO> getExpertisesByCategory(@PathVariable Long id)  throws Exception {

        User professional = this.getAuthenticatedUser();

        //lista de categorias
        List<Expertise> expertises = expertiseService.findExpertiseNotExistByUserAndCategory(professional.getId(), id);

        List<ExpertiseDTO> expertisesDTO = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        return expertisesDTO;
    }

    @GetMapping("/{expertiseId}/servicos")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    @ResponseBody
    public List<ServiceDTO> findServicesByExpertise(@PathVariable Long expertiseId)  throws Exception {

        User professional = this.getAuthenticatedUser();

        Expertise expertise = expertiseService.findById(expertiseId).orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));

        //lista de serviços
        List<Service> services = serviceService.findByExpertise(expertise);

        List<ServiceDTO> servicesDTO = services.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        return servicesDTO;
    }

    /**
     * Retorna uma especialidade para uma requisição ajax.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    @ResponseBody
    public ExpertiseDTO getExpertiseById(@PathVariable Long id)  throws Exception {

        User professional = this.getAuthenticatedUser();

        //lista de categorias
        Optional<Expertise> oExpertise = expertiseService.findById(id);

        if (!oExpertise.isPresent()) {
            return new ExpertiseDTO();
        }

        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(oExpertise.get());
        return expertiseDTO;
    }

    /**
     * Adiciona uma especialidade para o profissional.
     * @param expertiseDTO
     * @param errors
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @PostMapping()
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveExpertises(@Validated ChooseExpertiseDTO expertiseDTO, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {

        if (errors.hasErrors()) {
            log.error("Erro de validação de especialidade");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/profissional/especialidades/novo";
        }

        //verifica se a especialidade existe
        Long expertiseId = expertiseDTO.getId();
        Optional<Expertise> oExpertise = expertiseService.findById(expertiseDTO.getId());

        if(!oExpertise.isPresent()) {
        	throw new Exception("Não existe essa especialidade!");
        }

        User professional = this.getAuthenticatedUser();

        //verifica se o profissional já possui a especialidade
        Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(professional, oExpertise.get());

        if(oProfessionalExpertise.isPresent()) {
        	throw new Exception("Você já possui essa especialidade!");
        }

        //cria e adiciona a descrição personalizada para o profissional sobre a especialidade
        ProfessionalExpertise professionalExpertise = new ProfessionalExpertise(professional, oExpertise.get());
        professionalExpertise.setDescription(expertiseDTO.getDescription());

        professionalExpertiseService.save(professionalExpertise);

        return("redirect:/minha-conta/profissional/especialidades");
    }

    /**
     * Um profissional remove uma de suas especialidades.
     * @param id
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.USER})
    public String delete(@PathVariable Expertise id, RedirectAttributes redirectAttributes) throws Exception {
        log.debug("Removendo uma especialidade com id {}", id);
        User professional = this.getAuthenticatedUser();

        Optional <ProfessionalExpertise> optionalProfession = this.professionalExpertiseService.findByProfessionalAndExpertise(professional,id);

        if(!optionalProfession.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        this.professionalExpertiseService.delete(optionalProfession.get().getId());
        return "redirect:/minha-conta/profissional/especialidades";
    }

    /**
     * Trata uma requisição AJAX para alimentar o painel lateral com as informações estatísticas do profissional
     * @param expertiseId
     * @return
     * @throws Exception
     */
    @GetMapping("/estatistica/{id}")
    @RolesAllowed({RoleType.USER, RoleType.ADMIN})
    @ResponseBody
    public ResponseEntity<?> getExpertiseData(@PathVariable("id") Long expertiseId) {
        ResponseDTO response = new ResponseDTO();
        try {
            //verifica se o usuário está autenticado
            Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

            if (!oProfessional.isPresent()) {
                response.setMessage("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
                return ResponseEntity.status(401).body(response);
            }

            //se for 0, são todas as especialidades
            if(expertiseId > 0){
                //verifica se a especialidade pertence ao profissional
                Optional<Expertise> oExpertise = expertiseService.findById(expertiseId);
                Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(oProfessional.get(), oExpertise.get());
                if(!oProfessionalExpertise.isPresent()){
                    response.setMessage("A especialidade não pertence ao profissional.");
                    return ResponseEntity.status(400).body(response);
                }
                response.setData(templateUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId));
            }
            else{
                response.setData(templateUtil.getProfessionalStatisticInfo(oProfessional.get(), 0L));
            }

            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.setMessage("Erro ao recuperar dados do profissional. Por favor, tente novamente.");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * Retorna o indivíduo logado.
     * @return
     * @throws Exception
     */
    private User getAuthenticatedUser() throws Exception {
        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        return oProfessional.get();
    }

}
