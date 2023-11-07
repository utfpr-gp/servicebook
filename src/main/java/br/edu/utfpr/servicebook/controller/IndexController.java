package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.CategoryDTO;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO;
import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.CategoryMapper;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ServiceMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;

import br.edu.utfpr.servicebook.util.CookieNames;
import br.edu.utfpr.servicebook.util.SessionNames;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceMapper serviceMapper;
    @GetMapping
    @PermitAll
    public ModelAndView showIndexPage(HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("visitor/index");

        if(!(authentication.getAuthentication() instanceof AnonymousAuthenticationToken)){
            Optional<User> oUser = userService.findByEmail(authentication.getEmail());
            UserTemplateInfo userInfo = templateUtil.getUserInfo(oUser.get());
            mv.addObject("userInfo", userInfo);

            String accessType = (String) httpSession.getAttribute(SessionNames.ACCESS_USER_KEY);
            if(accessType == SessionNames.ACCESS_USER_PROFESSIONAL_VALUE){
                return new ModelAndView("redirect:/minha-conta/profissional/disponiveis");
            }

            if(accessType == SessionNames.ACCESS_USER_COMPANY_VALUE){
                return new ModelAndView("redirect:/minha-conta/empresa/disponiveis");
            }
        }
        //lista de categorias
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(s -> categoryMapper.toDto(s))
                .collect(Collectors.toList());

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);
        mv.addObject("totalCompanies", companyService.countAll());
        mv.addObject("totalJobRequests", jobRequestService.countAll());
        mv.addObject("totalExpertises", expertiseService.countAll());
        mv.addObject("totalJobContracted", jobContractedService.countAll());
        mv.addObject("totalProfessionals", userService.countProfessionals());
        mv.addObject("totalClients", userService.countUsersWithoutExpertise());
        mv.addObject("categories", categoryDTOs);

        return mv;
    }

    @GetMapping("/cliente")
    @PermitAll
    public String showClientPage(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        httpSession.setAttribute(SessionNames.ACCESS_USER_KEY, SessionNames.ACCESS_USER_CLIENT_VALUE);
        return "redirect:/";
    }

    @GetMapping("/bem-vindo")
    @PermitAll
    public ModelAndView showWelcomePage() {
        ModelAndView mv = new ModelAndView("visitor/welcome");
        return mv;
    }

    @GetMapping("/como-funciona")
    @PermitAll
    public String showHowWorks() {
        return "visitor/how-works";
    }

    @GetMapping("/nao-autorizado")
    @PermitAll
    public String notAuthorized() {
        return "error/not-authorized";
    }

    @GetMapping("/especialidades/categoria/{categoryId}")
    @PermitAll
    @ResponseBody
    public List<ExpertiseDTO> showExpertisesByCategory(@PathVariable Long categoryId)  throws Exception {
        Optional<Category> oCategory = categoryService.findById(categoryId);

        Category category = categoryService.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        List<Expertise> expertises = expertiseService.findByCategoryId(categoryId);

        List<ExpertiseDTO> expertiseDTOS = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        return expertiseDTOS;
    }

    @GetMapping("/servicos/especialidade/{expertiseId}")
    @PermitAll
    @ResponseBody
    public List<ServiceDTO> showServicesByExpertise(@PathVariable Long expertiseId)  throws Exception {

        Expertise expertise = expertiseService.findById(expertiseId).orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));

        List<Service> services = serviceService.findByExpertise(expertise);

        List<ServiceDTO> servicesDTO = services.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        return servicesDTO;
    }
}