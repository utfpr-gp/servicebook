package br.edu.utfpr.servicebook.filter;

import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.service.FollowsService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Filtro para adicionar informações de estatística do usuário logado no template.
 */
public class TemplateStatisticInfoFilter implements Filter {

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private FollowsService followsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //usado para injetar com Autowired em filtros
        //como os filtros são do Servlet e não do Spring, o Autowired não é habilitado naturalmente.
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession httpSession = req.getSession();

        Optional<User> oUser = userService.findByEmail(authentication.getEmail());

        if(oUser.isPresent()){
            UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(oUser.get());
            request.setAttribute("statisticInfo", statisticInfo);
        }

        //antes do chain - executa na requisição
        chain.doFilter(request, response);
        //depois do chain - executa na resposta do servidor
    }
}