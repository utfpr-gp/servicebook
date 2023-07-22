package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.CookieNames;
import br.edu.utfpr.servicebook.util.SessionNames;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private HttpSession httpSession;

    @GetMapping
    @PermitAll
    public ModelAndView showIndexPage(HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("visitor/index");

        if(!(authentication.getAuthentication() instanceof AnonymousAuthenticationToken)){
            Optional<User> oUser = userService.findByEmail(authentication.getEmail());
            UserTemplateInfo userInfo = templateUtil.getUserInfo(oUser.get());
            mv.addObject("userInfo", userInfo);

            String accessType = (String) httpSession.getAttribute(SessionNames.ACCESS_USER_KEY);
            if(accessType == SessionNames.ACCESS_USER_PROFESSIONAL_VALUE){
                return new ModelAndView("redirect:/minha-conta/profissional/disponiveis");
            }
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

    @GetMapping("/cliente")
    @PermitAll
    public String showClientPage(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        httpSession.setAttribute(SessionNames.ACCESS_USER_KEY, SessionNames.ACCESS_USER_CLIENT_VALUE);
        return "redirect:/";
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

    @GetMapping("/nao-autorizado")
    @PermitAll
    public String notAuthorized() {
        return "error/not-authorized";
    }

}