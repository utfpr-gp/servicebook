package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ModelAndView showIndexPage() {

        ModelAndView mv = new ModelAndView("visitor/index");

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        return mv;
    }

    @GetMapping("/bem-vindo")
    public ModelAndView showWelcomePage() {
        ModelAndView mv = new ModelAndView("visitor/welcome");
        return mv;
    }

}
