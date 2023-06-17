package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/profissional/especialidades")
@Controller
public class ProfessionalExpertiseController {

    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private ProfessionalExpertiseMapper professionalExpertiseMapper;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private IAuthentication authentication;

    /**
     * Apresenta a tela para o profissional adicionar especialidades.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping()
    @RolesAllowed({RoleType.USER})
    public ModelAndView showExpertises(@RequestParam(required = false, defaultValue = "0") Optional<Long> id)  throws Exception {

        User professional = this.getProfessional();
        UserDTO professionalMinDTO = userMapper.toDto(professional);

        ModelAndView mv = new ModelAndView("professional/my-expertises");

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        UserTemplateInfo userTemplateInfo = templateUtil.getUserInfo(professionalMinDTO);
        UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(professional, id.get());

        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("userInfo", userTemplateInfo);
        mv.addObject("currentExpertiseId", id.orElse(0L));

        List<ExpertiseDTO> professionalExpertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());
        List<Expertise> professionPage = expertiseService.findExpertiseNotExist(getProfessional().getId());

        List<ExpertiseDTO> otherExpertisesDTOs = professionPage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        mv.addObject("otherExpertises", otherExpertisesDTOs);
        mv.addObject("professionalExpertises", professionalExpertiseDTOs);
        return mv;
    }

    @PostMapping()
    @RolesAllowed({RoleType.USER})
    public ModelAndView saveExpertises(@Valid ProfessionalExpertiseDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView mv = new ModelAndView("redirect:especialidades");
        Set<Integer> ids = dto.getIds();

        if (ids == null) {
            return mv;
        }

        User professional = this.getProfessional();

        for (int id : ids) {
            Optional<Expertise> e = expertiseService.findById((Long.valueOf(id)));

            if (!e.isPresent()) {
                throw new Exception("Não existe essa especialidade!");
            }
            ProfessionalExpertise p = professionalExpertiseService.save(new ProfessionalExpertise(professional, e.get()));
        }
        return mv;
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
        User professional = this.getProfessional();

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
    @ResponseBody
    @RolesAllowed({RoleType.USER})
    public UserTemplateStatisticInfo getExpertiseData(@PathVariable("id") Long expertiseId) throws Exception {
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        return templateUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId);
    }

    /**
     * Retorna o indivíduo logado.
     * @return
     * @throws Exception
     */
    private User getProfessional() throws Exception {
        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Opss! Não foi possivel encontrar seus dados, tente fazer login novamente");
        }

        return oProfessional.get();
    }

}
