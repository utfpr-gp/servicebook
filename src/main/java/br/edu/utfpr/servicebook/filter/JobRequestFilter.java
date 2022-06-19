package br.edu.utfpr.servicebook.filter;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.model.repository.IndividualRepository;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Filtro para cadastrar no banco de dados o JobRequest preenchido quando o usuário não estava logado.
 * Estes dados ficam na sessão.
 * Quando o usuário faz o login, os dados do JobRequest que estão na sessão, são validados e cadastrados no banco de dados e então,
 * o usuário é encaminhado para a página com esta informação.
 * Caso os dados não sejam válidos, não persiste no BD.
 */
public class JobRequestFilter implements Filter {

    @Autowired
    private WizardSessionUtil<JobRequestDTO> wizardSessionUtil;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private IndividualRepository individualService;

    @Autowired
    private ExpertiseService expertiseService;

    /**
     * Usado para validar manualmente o DTO que contém anotações de validações
     */
    @Autowired
    private SmartValidator validator;

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

        System.out.println("Filtro de JobRequest");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession httpSession = req.getSession();

        JobRequestDTO jobRequestDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);
        System.out.println(jobRequestDTO);

        //valida o jobRequestDTO para ver se está completo.
        Errors errors = new BeanPropertyBindingResult(jobRequestDTO, "jobRequestDTO");
        validator.validate(jobRequestDTO, errors);

        /*
            Se estiver válido, salva no BD e deixar a requisição seguir seu fluxo..
            Caso contrário, deixar a requisição seguir seu fluxo.
         */
        if(!errors.hasErrors() && jobRequestDTO.getDescription() != null){

            jobRequestDTO.setStatus(String.valueOf(JobRequest.Status.AVAILABLE));

            Optional<Individual> optionalIndividual = individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail());

            Optional<Expertise> optionalExpertise = expertiseService.findById(jobRequestDTO.getExpertiseId());

            //salva no BD
            JobRequest jr = jobRequestMapper.toEntity(jobRequestDTO);

            jr.setIndividual(optionalIndividual.get());

            jr.setExpertise(optionalExpertise.get());

            jobRequestService.save(jr);

            //TODO redirecionar para a página de aviso de sucesso de cadastro de um jobRequest
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("/servicebook/requisicoes/pedido-recebido");
        }

        //antes do chain - executa na requisição
        chain.doFilter(request, response);
        //depois do chain - executa na resposta do servidor
    }


}