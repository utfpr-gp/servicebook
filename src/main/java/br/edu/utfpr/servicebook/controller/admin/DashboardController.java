package br.edu.utfpr.servicebook.controller.admin;

import br.edu.utfpr.servicebook.model.dto.DashFilterBodyMinDto;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.*;
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
                jobRequestService.countByStatus(JobRequest.Status.TO_DO);
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
        if(startDate.isEmpty() && expertiseId.equals(0)){
            return "redirect:/a/dashboard";
        }

        System.out.println("expertiseId: " + expertiseId);
        System.out.println("startDate: " + startDate);

        //valida a data
        if(!startDate.isEmpty()){
            Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            if(!DATE_PATTERN.matcher(startDate).matches()){
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
        if(expertiseId != null){
            Expertise expertise = expertiseService.findById(expertiseId).orElseThrow(() -> new IllegalArgumentException("A expertise informada não existe"));
            if(expertise == null){
                attributes.addFlashAttribute("msgError", "A expertise informada não existe");
                return "redirect:/a/dashboard/q";
            }
        }


        model.addAttribute("totalCompanies", companyService.countAll());
        model.addAttribute("totalProfessionals", userService.countProfessionals());
        model.addAttribute("totalClients", userService.countUsersWithoutExpertise());
        Long totalUsers = userService.countAll();
        model.addAttribute("totalUsers", totalUsers);

        Long totalJobAvailable = jobRequestService.countByStatus(JobRequest.Status.AVAILABLE) +
                jobRequestService.countByStatus(JobRequest.Status.BUDGET);
        model.addAttribute("totalJobAvailable", totalJobAvailable);

        Long totalJobDoing = jobRequestService.countByStatus(JobRequest.Status.TO_DO) +
                jobRequestService.countByStatus(JobRequest.Status.TO_HIRED) +
                jobRequestService.countByStatus(JobRequest.Status.TO_DO);
        model.addAttribute("totalJobDoing", totalJobDoing);

        Long totalJobClosed = jobRequestService.countByStatus(JobRequest.Status.CLOSED) +
                jobRequestService.countByStatus(JobRequest.Status.CANCELED);
        model.addAttribute("totalJobClosed", totalJobClosed);

        Long totalJobs = totalJobAvailable + totalJobDoing + totalJobClosed;
        model.addAttribute("totalJobs", totalJobs);

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        //para preenchimento dos selects de filtro
        model.addAttribute("expertises", expertiseDTOs);
        model.addAttribute("months", getLastMonthsToSelectOptions());

        return "admin/dashboard-filtered";
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
            String monthYear = monthAgo.getMonth().name().substring(0, 3) + "/" + formattedDate.substring(0, 2);
            monthYearMap.put(formattedDate, monthYear);
        }

        return monthYearMap;
    }

    /**
     * Retorna o último dia do mês da data passada como parâmetro
     * @param date
     * @return
     */
    public LocalDate getLastDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }


}
