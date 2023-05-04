package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.model.repository.UserRepository;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelCompanyDTO;
import br.edu.utfpr.servicebook.util.sidePanel.TemplateUtil;
import br.edu.utfpr.servicebook.util.sidePanel.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.sidePanel.UserTemplateStatisticDTO;
import com.cloudinary.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/empresa/profissionais")
@Controller
public class CompanyProfessionalController {

    public static final Logger log = LoggerFactory.getLogger(CompanyProfessionalController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyProfessionalService companyProfessionalService;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyProfessionalMapper companyProfessionalMapper;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Apresenta a tela para a empresa adicionar profissionais.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping()
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView showProfessionals(@RequestParam(required = false, defaultValue = "0") Optional<Long> id)  throws Exception {

        User company = this.getCompany();
        UserDTO professionalMinDTO = userMapper.toDto(company);

        ModelAndView mv = new ModelAndView("company/new-professional");

        UserTemplateInfo userTemplateInfo = templateUtil.getUserInfo(professionalMinDTO);
        SidePanelCompanyDTO sidePanelStatisticDTO = templateUtil.getCompanyStatisticInfo(company, id.get());

        mv.addObject("statisticInfo", sidePanelStatisticDTO);
        mv.addObject("individualInfo", userTemplateInfo);

        mv.addObject("id", id.orElse(0L));

        List<User> professionals = userService.findProfessionalsNotExist();

        List<CompanyProfessional> companyProfessionals = companyProfessionalService.findByCompany(company);

        List<CompanyProfessionalDTO2> companyProfessionalDTO2s = companyProfessionals.stream()
                .map(s -> companyProfessionalMapper.toResponseDTO(s))
                .collect(Collectors.toList());


        mv.addObject("expertises", company);
        mv.addObject("professionals", professionals);
        mv.addObject("professionalExpertises", companyProfessionalDTO2s);
        return mv;
    }

    @PostMapping()
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView saveProfessionals(@Valid CompanyProfessionalDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView mv = new ModelAndView("redirect:profissionais");
        Set<Integer> ids = dto.getIds();

        if (ids == null) {
            return mv;
        }

        User company = this.getCompany();
//
        for (int id : ids) {
            Optional<User> e = userService.findById((Long.valueOf(id)));

            if (!e.isPresent()) {
                throw new Exception("Não existe esse profissional!");
            }
            CompanyProfessional p = companyProfessionalService.save(new CompanyProfessional(company, e.get()));
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable User id, RedirectAttributes redirectAttributes) throws Exception {

        log.debug("Removendo um profissional com id {}", id);
//        User company = this.getCompany();
//
//        Optional <CompanyProfessional> optionalProfession = this.companyProfessionalService.findByCompanyAndProfessional(company,id);
//
//        if(!optionalProfession.isPresent()){
//            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
//        }
//
//        this.companyProfessionalService.delete(optionalProfession.get().getId());
        return "redirect:/minha-conta/empresa/profissionais";
    }

    /**
     * Retorna a empresa logado.
     * @return
     * @throws Exception
     */
    private User getCompany() throws Exception {
        Optional<User> oCompany = (userService.findByEmail(authentication.getEmail()));

        if (!oCompany.isPresent()) {
            throw new Exception("Opss! Não foi possivel encontrar seus dados, tente fazer login novamente");
        }

        return oCompany.get();
    }
}
