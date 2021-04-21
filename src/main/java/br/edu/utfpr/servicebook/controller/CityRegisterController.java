package br.edu.utfpr.servicebook.controller;


import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RequestMapping("/cadastrar-cidade")
@Controller
public class CityRegisterController {

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @GetMapping
    public ModelAndView showForm(){
        ModelAndView mv = new ModelAndView("city-register");
        List<State> states = stateService.findAll();

        mv.addObject("states", states);
        return mv;
    }

    @PostMapping
    public ModelAndView save(@RequestParam("name_city") String name_city, @RequestParam("id_state") String id_state){
        Long id = Long.parseLong(id_state);

        City city = new City();
        city.setName(name_city);
        city.setIdState(id);
        cityService.save(city);

        return new ModelAndView("redirect:cadastrar-cidade");
    }
}

