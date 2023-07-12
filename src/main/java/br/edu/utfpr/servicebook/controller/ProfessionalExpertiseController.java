package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showExpertises(@RequestParam(required = false, defaultValue = "0") Optional<Long> id)  throws Exception {

        User professional = this.getAuthenticatedUser();

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(professional, id.get());

        List<ExpertiseDTO> professionalExpertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        List<Expertise> professionPage = expertiseService.findExpertiseNotExist(getAuthenticatedUser().getId());

        List<ExpertiseDTO> otherExpertisesDTOs = professionPage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("professional/my-expertises");
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("currentExpertiseId", id.orElse(0L));
        mv.addObject("otherExpertises", otherExpertisesDTOs);
        mv.addObject("professionalExpertises", professionalExpertiseDTOs);
        return mv;
    }

    @PostMapping()
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView saveExpertises(@Valid List<Integer> ids, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView mv = new ModelAndView("redirect:especialidades");

        if (ids == null) {
            return mv;
        }

        User professional = this.getAuthenticatedUser();

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
