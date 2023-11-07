package br.edu.utfpr.servicebook.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

@RequestMapping("/erro")
@Controller
public class ErrorController {

    @Autowired
    private HttpSession httpSession;

    @GetMapping()
    @PermitAll
    public String showDefault() {
        return "error/error-handler";
    }

    @GetMapping("/acesso-negado")
    @PermitAll
    public String showError() {
        return "error/not-authorized";
    }

    @GetMapping("/nao-encontrado")
    @PermitAll
    public String showNotFound() {
        return "error/not-found";
    }

    @GetMapping("/nao-autorizado")
    @PermitAll
    public String showUnauthorized() {
        return "error/not-authorized";
    }

    @GetMapping("/persistencia")
    @PermitAll
    public String showPersistenceError() {
        return "error/error-database";
    }

    @GetMapping("/servidor")
    @PermitAll
    public String showServerError() {
        return "error/error-server";
    }

    @GetMapping("/cliente")
    @PermitAll
    public String showClientError() {
        return "error/4xx";
    }

    @GetMapping("/405")
    @PermitAll
    public String show405Error() {
        return "error/error-405";
    }
}