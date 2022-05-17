package br.edu.utfpr.servicebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/entrar")
@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public ModelAndView showLogin() {
        log.debug("Servicebook: Login.");

        ModelAndView mv = new ModelAndView("visitor/login");

        return mv;
    }

}
