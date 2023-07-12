package br.edu.utfpr.servicebook.controller.admin;

import br.edu.utfpr.servicebook.model.dto.DashFilterBodyMinDto;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.ProfileEnum;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequestMapping("/a")
@Controller
public class DashboardController {
    public static final Logger log =
            LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @GetMapping()
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView index(){

        ModelAndView mv = new ModelAndView("admin/index");
        return mv;
    }

    /**
     * Mostra a página inicial do dashboard
     *
     * @return
     */
    @GetMapping("/dashboard")
    @RolesAllowed({RoleType.ADMIN})
    public ModelAndView showDashboard(){

        ModelAndView mv = new ModelAndView("admin/dashboard");

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        mv.addObject("totalCompanies", companyService.countAll());
        mv.addObject("totalProfessionals", userService.countProfessionals());
        mv.addObject("totalClients", userService.countUsersWithoutExpertise());
        Long totalUsers = userService.countAll();
        mv.addObject("totalUsers", totalUsers);

        Long totalJobAvailable = jobRequestService.countByStatus(JobRequest.Status.AVAILABLE) +
                jobRequestService.countByStatus(JobRequest.Status.BUDGET);
        mv.addObject("totalJobAvailable", totalJobAvailable);

        Long totalJobDoing = jobRequestService.countByStatus(JobRequest.Status.TO_DO) +
                jobRequestService.countByStatus(JobRequest.Status.TO_HIRED) +
                jobRequestService.countByStatus(JobRequest.Status.DOING);
        mv.addObject("totalJobDoing", totalJobDoing);

        Long totalJobClosed = jobRequestService.countByStatus(JobRequest.Status.CLOSED) +
                jobRequestService.countByStatus(JobRequest.Status.CANCELED);
        mv.addObject("totalJobClosed", totalJobClosed);

        Long totalJobs = totalJobAvailable + totalJobDoing + totalJobClosed;
        mv.addObject("totalJobs", totalJobs);

        //para preenchimento dos selects de filtro
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("months", getLastMonthsToSelectOptions());

        return mv;
    }

    @GetMapping("/dashboard/q")
    @RolesAllowed({RoleType.ADMIN})
    public String filterDashboard(@RequestParam(value = "startDate", defaultValue = "") String startDate,
                                  @RequestParam(value = "expertiseId", required = false) Long expertiseId,
                                  RedirectAttributes attributes,
                                  Model model){

        //valida os parâmetros, caso sejam vazios, encaminha para a tela inicial
        if(startDate.isEmpty() && expertiseId == null){
            return "redirect:/a/dashboard";
        }

        //valida a data
        if(!startDate.isEmpty()) {
            Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            if (!DATE_PATTERN.matcher(startDate).matches()) {
                attributes.addFlashAttribute("msgError", "A data está com formato inválido");
                //return ResponseEntity.badRequest().body("A data está com formato inválido").toString();
                return "redirect:/a/dashboard/q";
            }

            try {
                LocalDate.parse(startDate);
            } catch (DateTimeParseException e) {
                attributes.addFlashAttribute("msgError", "A data está com formato inválido");
                //return ResponseEntity.badRequest().body("A data está com formato inválido").toString();
                return "redirect:/a/dashboard/q";
            }
        }

        //valida a expertise
        Expertise expertise = null;
        if(expertiseId != null) {
            expertise = expertiseService.findById(expertiseId).orElseThrow(() -> new IllegalArgumentException("A expertise informada não existe"));
            if (expertise == null) {
                attributes.addFlashAttribute("msgError", "A expertise informada não existe");
                return "redirect:/a/dashboard/q";
            }
        }

        //para preenchimento dos selects de filtro
        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        model.addAttribute("expertises", expertiseDTOs);
        model.addAttribute("months", getLastMonthsToSelectOptions());

        //faz a filtragem por data e expertise
        if(!startDate.isEmpty() && expertiseId != null){
            LocalDate startLocalDate = LocalDate.parse(startDate);
            LocalDate endLocalDate = getLastDayOfMonth(startLocalDate);
            model = filterDashboardByDateAndExpertise(startLocalDate, endLocalDate, expertise, model);
            return "admin/dashboard-filtered";
        }

        //faz a filtragem por data
        if(!startDate.isEmpty()){
            LocalDate startLocalDate = LocalDate.parse(startDate);
            LocalDate endLocalDate = getLastDayOfMonth(startLocalDate);
            model = filterDashboardByDate(startLocalDate, endLocalDate, model);
            return "admin/dashboard-filtered";
        }

        //faz a filtragem por expertise
        if(expertiseId != null){
            model = filterDashboardByExpertise(expertise, model);
            return "admin/dashboard-filtered";
        }

        //caso não tenha sido feita nenhuma filtragem, retorna para a tela inicial
        return "redirect:/a/dashboard";
    }


    /**
     * Gera a lista dos últimos 6 meses para o filtro de data.
     * A chave do Map guarda o primeiro dia de cada mês no formato yyyy-MM-dd
     * O value da Map guarda o mês e o ano no formato textual Mês/Ano
     * @return
     */
    public Map<String, String> getLastMonthsToSelectOptions() {
        LocalDate currentDate = LocalDate.now();
        Map<String, String> monthYearMap = new LinkedHashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 6; i++) {
            LocalDate monthAgo = currentDate.minusMonths(i).withDayOfMonth(1);
            String formattedDate = monthAgo.format(formatter);
            String monthYear = monthAgo.getMonth().name().substring(0, 3) + "/" + formattedDate.substring(2, 4);
            monthYearMap.put(formattedDate, monthYear);
        }

        return monthYearMap;
    }

    public String getToStringDateQueryParam(int month, int year){
        String[] months = {"","Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};

        return months[month] + "/" + year;
    }


    /**
     * Retorna o último dia do mês da data passada como parâmetro
     * @param date
     * @return
     */
    public LocalDate getLastDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    /**
     * Faz a filtragem dos dados para um dado mês
     * @param startLocalDate
     * @param model
     */
    public Model filterDashboardByDate(LocalDate startLocalDate, LocalDate endLocalDate, Model model) {

        //total de jobs criados no período
        Long totalJobs = jobRequestService.countByDateCreatedBetween(startLocalDate, endLocalDate);
        model.addAttribute("totalJobs", totalJobs);

        //total de jobs criados no período e com status AVAILABLE ou BUDGET
        Long totalJobAvailable = jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.AVAILABLE, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.BUDGET, startLocalDate, endLocalDate);
        model.addAttribute("totalJobAvailable", totalJobAvailable);

        //total de jobs criados no período e com status TO_DO, TO_HIRED ou DOING
        Long totalJobDoing = jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.TO_DO, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.TO_HIRED, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.DOING, startLocalDate, endLocalDate);
        model.addAttribute("totalJobDoing", totalJobDoing);

        //total de jobs criados no período e com status CLOSED ou CANCELED
        Long totalJobClosed = jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.CLOSED, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndDateCreatedBetween(JobRequest.Status.CANCELED, startLocalDate, endLocalDate);
        model.addAttribute("totalJobClosed", totalJobClosed);

        return model;
    }

    /**
     * Faz a filtragem dos dados para uma dada expertise
     * @param expertise
     * @param model
     * @return
     */
    private Model filterDashboardByExpertise(Expertise expertise, Model model){

        //total de jobs de uma dada especialidade
        Long totalJobs = jobRequestService.countByExpertise(expertise);
        model.addAttribute("totalJobs", totalJobs);

        //total de jobs de uma dada especialidade e com status AVAILABLE ou BUDGET
        Long totalJobAvailable = jobRequestService.countByStatusAndExpertise(JobRequest.Status.AVAILABLE, expertise) +
                jobRequestService.countByStatusAndExpertise(JobRequest.Status.BUDGET, expertise);
        model.addAttribute("totalJobAvailable", totalJobAvailable);

        //total de jobs de uma dada especialidade e com status TO_DO, TO_HIRED ou DOING
        Long totalJobDoing = jobRequestService.countByStatusAndExpertise(JobRequest.Status.TO_DO, expertise) +
                jobRequestService.countByStatusAndExpertise(JobRequest.Status.TO_HIRED, expertise) +
                jobRequestService.countByStatusAndExpertise(JobRequest.Status.DOING, expertise);
        model.addAttribute("totalJobDoing", totalJobDoing);

        //total de jobs de uma dada especialidade e com status CLOSED ou CANCELED
        Long totalJobClosed = jobRequestService.countByStatusAndExpertise(JobRequest.Status.CLOSED, expertise) +
                jobRequestService.countByStatusAndExpertise(JobRequest.Status.CANCELED, expertise);
        model.addAttribute("totalJobClosed", totalJobClosed);

        return model;
    }

    /**
     * Faz a filtragem dos dados para um dado mês e uma dada expertise
     * @param startLocalDate
     * @param endLocalDate
     * @param expertise
     * @param model
     * @return
     */
    private Model filterDashboardByDateAndExpertise(LocalDate startLocalDate, LocalDate endLocalDate, Expertise expertise, Model model){

        //total de jobs de uma dada especialidade e no período
        Long totalJobs = jobRequestService.countByExpertiseAndDateCreatedBetween(expertise, startLocalDate, endLocalDate);
        model.addAttribute("totalJobs", totalJobs);

        //total de jobs de uma dada especialidade e no período e com status AVAILABLE ou BUDGET
        Long totalJobAvailable = jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.AVAILABLE, expertise, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.BUDGET, expertise, startLocalDate, endLocalDate);
        model.addAttribute("totalJobAvailable", totalJobAvailable);

        //total de jobs de uma dada especialidade e no período e com status TO_DO, TO_HIRED ou DOING
        Long totalJobDoing = jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.TO_DO, expertise, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.TO_HIRED, expertise, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.DOING, expertise, startLocalDate, endLocalDate);
        model.addAttribute("totalJobDoing", totalJobDoing);

        //total de jobs de uma dada especialidade e no período e com status CLOSED ou CANCELED
        Long totalJobClosed = jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.CLOSED, expertise, startLocalDate, endLocalDate) +
                jobRequestService.countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status.CANCELED, expertise, startLocalDate, endLocalDate);
        model.addAttribute("totalJobClosed", totalJobClosed);

        return model;
    }



}
