package br.edu.utfpr.servicebook.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     *
     * Trata de erros gerados pelo banco de dados.
     * Note que @ExceptionHandler recebe duas classes como argumentos.
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class})
    public String handleDatabaseException(HttpServletRequest req, Exception e) {
        return "error/error-database";
    }
}
