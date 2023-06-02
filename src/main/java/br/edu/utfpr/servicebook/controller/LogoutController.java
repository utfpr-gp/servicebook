package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.LoginDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.UserCodeMapper;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("/logout")
@Controller
public class LogoutController {

    @GetMapping
    @PermitAll
    public String doLogout(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
