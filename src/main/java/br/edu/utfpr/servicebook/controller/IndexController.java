package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.CompanyProfessional;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.entity.UserToken;
import br.edu.utfpr.servicebook.model.mapper.UserTokenMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private CompanyProfessionalService companyProfessionalService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private UserTokenMapper userTokenMapper;
    @GetMapping
    @PermitAll
    public ModelAndView showIndexPage() {

        ModelAndView mv = new ModelAndView("visitor/index");
        System.out.println("Principal: " + authentication.getAuthentication().getPrincipal());
        if(!(authentication.getAuthentication() instanceof AnonymousAuthenticationToken)){
            Optional<User> oUser = userService.findByEmail(authentication.getEmail());
            UserTemplateInfo userInfo = templateUtil.getUserInfo(oUser.get());
            mv.addObject("userInfo", userInfo);
        }

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);


        mv.addObject("totalCompanies", companyService.countAll());
        mv.addObject("totalJobRequests", jobRequestService.countAll());
        mv.addObject("totalExpertises", expertiseService.countAll());
        mv.addObject("totalJobContracted", jobContractedService.countAll());
        mv.addObject("totalProfessionals", userService.countProfessionals());
        mv.addObject("totalClients", userService.countUsersWithoutExpertise());

        return mv;
    }

    @GetMapping("/bem-vindo")
    @PermitAll
    public ModelAndView showWelcomePage() {
        ModelAndView mv = new ModelAndView("visitor/welcome");
        return mv;
    }

    @GetMapping("/como-funciona")
    @PermitAll
    public String showHowWorks() {
        return "visitor/how-works";
    }

    @GetMapping("/confirmar")
    @PermitAll
    public ModelAndView saveProfessionalsConfirm(
            @RequestParam(value = "code", required = false) String token,
            HttpSession httpSession,
            RedirectAttributes redirectAttributes
    ) throws Exception{
        if(token != null){
            UserToken userToken = userTokenService.findByUserToken(token);

            Optional<User> oProfessional = userService.findByEmail(userToken.getEmail());

            if(!oProfessional.isPresent()){
                throw new EntityNotFoundException("O usuário não foi encontrado!");
            }

            User user_professional = oProfessional.get();
            user_professional.setConfirmed(true);
            userService.save(user_professional);

            if(user_professional.isConfirmed()){
                Optional<CompanyProfessional> companyProfessional = companyProfessionalService.findByCompanyAndProfessional(userToken.getUser(), oProfessional.get());

                CompanyProfessional companyProfessional1 = companyProfessional.get();
                companyProfessional1.setConfirmed(true);
                companyProfessionalService.save(companyProfessional1);
//                CompanyProfessional p = companyProfessionalService.save(new CompanyProfessional(userToken.getUser(), oProfessional.get()));
            }

            redirectAttributes.addFlashAttribute("msg", "Você foi incluido na empresa com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Token não existe!");
        }

        return new ModelAndView("redirect:/");
    }
}