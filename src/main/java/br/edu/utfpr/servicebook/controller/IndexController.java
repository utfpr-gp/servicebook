package br.edu.utfpr.servicebook.controller;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import br.edu.utfpr.servicebook.util.CPFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ProfessionalService professionalService;


    @GetMapping
    public ModelAndView showForm(){
        ModelAndView mv = new ModelAndView("visitor/index");
        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        return mv;
    }
}

