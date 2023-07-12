package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ResponseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
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

@RequestMapping("/minha-conta/profissional/servicos")
@Controller
public class ProfessionalServiceController {
    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);
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

    @GetMapping("/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showExpertises(@PathVariable Long id)  throws Exception {

        User user = this.getAuthenticatedUser();

        Expertise expertise = expertiseService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));

        ExpertiseDTO expertiseDTO = expertiseMapper.toDto(expertise);

        UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(user, id);

        ModelAndView mv = new ModelAndView("professional/my-services");
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("expertiseDTO", expertiseDTO);

        return mv;
    }


    /**
     * Retorna o usuário logado.
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
