package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.CompanyService;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelCompanyDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelIndividualDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private CityService cityService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private SidePanelUtil sidePanelUtil;

    @Autowired
    private IAuthentication authentication;
    @GetMapping
    @PermitAll
    public ModelAndView showIndexPage() {

        ModelAndView mv = new ModelAndView("visitor/index");
        System.out.println("Principal: " + authentication.getAuthentication().getPrincipal());
        if(!(authentication.getAuthentication() instanceof AnonymousAuthenticationToken)){
            Optional<Individual> oIndividual = individualService.findByEmail(authentication.getEmail());
            SidePanelIndividualDTO individualInfo = sidePanelUtil.getIndividualInfo(oIndividual.get());
            mv.addObject("individualInfo", individualInfo);
        }

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

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

}
