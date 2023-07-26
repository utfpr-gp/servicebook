//package br.edu.utfpr.servicebook.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.persistence.EntityNotFoundException;
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolationException;
//
//
//@ControllerAdvice
//public class GlobalRedirectExceptionHandler {
//	private final static Logger log = LoggerFactory.getLogger(GlobalRedirectExceptionHandler.class);
//
//	/**
//	 *
//	 * Tratador genérico de exceptions.
//	 * Trata de qualquer exceptions, caso não haja tratador mais específico.
//	 * @param req
//	 * @param e
//	 * @return
//	 * @throws Exception
//	 */
//	@ExceptionHandler(value = Exception.class)
//	public String defaultErrorHandler(HttpServletRequest req, Exception e, RedirectAttributes redirectAttributes) throws Exception {
//
//		log.error("[URL] : {}", req.getRequestURL(), e);
//
//		redirectAttributes.addAttribute("message", e.getMessage());
//		redirectAttributes.addAttribute("url", req.getRequestURL());
//		return "redirect:/erro";
//	}
//
//	/**
//	 * Disparado quando não é possível encontrar os dados do usuário na sessão.
//	 * AccessDeniedException é lançado pelo Spring ao acessar rota sem permissão
//	 * AuthenticationCredentialsNotFoundException lançado pelo desenvolvedor ao não encontrar o usuário na sesão
//	 * @param e
//	 * @return
//	 */
//	@ExceptionHandler({AuthenticationCredentialsNotFoundException.class})
//	public String handleException(AuthenticationCredentialsNotFoundException e, HttpServletRequest req, RedirectAttributes redirectAttributes) {
//
//		redirectAttributes.addAttribute("message", e.getMessage());
//		redirectAttributes.addAttribute("url", req.getRequestURL());
//
//		return "redirect:/erro/acesso-negado";
//	}
//
//	/**
//	 * Em caso de acesso negado, redireciona para o login
//	 * @param e
//	 * @param req
//	 * @return
//	 */
//	@ExceptionHandler({AccessDeniedException.class})
//	public String handleException(AccessDeniedException e, HttpServletRequest req, RedirectAttributes redirectAttributes) {
//		redirectAttributes.addAttribute("msg", "Acesso negado");
//		redirectAttributes.addAttribute("url", req.getRequestURL());
//		return "redirect:/login";
//	}
//
//	@ExceptionHandler({UsernameNotFoundException.class})
//	public String handleException(HttpServletRequest req, UsernameNotFoundException e, RedirectAttributes redirectAttributes) {
//		redirectAttributes.addAttribute("message", e.getMessage());
//		redirectAttributes.addAttribute("url", req.getRequestURL());
//		return "redirect:/erro";
//	}
//
//	@ExceptionHandler(value = InvalidParamsException.class)
//	public String invalidException(HttpServletRequest req, Exception e, RedirectAttributes redirectAttributes) throws Exception {
//
//		log.error("[URL] : {}", req.getRequestURL(), e);
//
//		if(e.getMessage() != null) {
//			redirectAttributes.addAttribute("message", e.getMessage());
//			redirectAttributes.addAttribute("url", req.getRequestURL());
//			return "redirect:/erro";
//		}
//
//		return "redirect:/erro/cliente";
//	}
//
//	/**
//	 *
//	 * Tratador de erro mais específico.
//	 * Entidade não encontrada pelo id.
//	 * @param req
//	 * @param e
//	 * @return
//	 * @throws Exception
//	 */
//	@ExceptionHandler(value = EntityNotFoundException.class)
//	public String entityNotFound(HttpServletRequest req, Exception e, RedirectAttributes redirectAttributes) throws Exception {
//
//		log.error("[URL] : {}", req.getRequestURL(), e);
//
//		if(e.getMessage() != null) {
//			redirectAttributes.addAttribute("message", e.getMessage());
//			redirectAttributes.addAttribute("url", req.getRequestURL());
//			return "redirect:/erro";
//		}
//
//		return "redirect:/erro/nao-encontrado";
//	}
//
//	/**
//	 *
//	 * Trata do código de status 405.
//	 *
//	 * @param request
//	 * @param e
//	 * @return
//	 */
//	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//	public String handleError405(HttpServletRequest request, Exception e) {
//		return "redirect:/erro/405";
//	}
//
//	/**
//	 *
//	 * Trata de erros gerados pelo banco de dados.
//	 * Note que @ExceptionHandler recebe duas classes como argumentos.
//	 * @param req
//	 * @param e
//	 * @return
//	 */
//	@ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class})
//	public String handleDatabaseException(HttpServletRequest req, Exception e, RedirectAttributes redirectAttributes) {
//
//		return "redirect:/erro/persistencia";
//	}
//}
