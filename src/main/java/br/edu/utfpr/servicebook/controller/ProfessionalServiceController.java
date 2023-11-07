package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.controller.admin.ExpertiseController;
import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Service;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/servicos")
@Controller
public class ProfessionalServiceController {
    public static final Logger log =
            LoggerFactory.getLogger(ProfessionalServiceController.class);

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

    /**
     * Retorna o serviço de acordo com o id para uma requisiçao ajax.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({RoleType.USER})
    @ResponseBody
    public ServiceDTO findById(@PathVariable("id") Long id){

        if(id < 0){
            throw new InvalidParamsException("O identificador não pode ser negativo.");
        }

        Optional<Service> oService = serviceService.findById(id);

        if(!oService.isPresent()){
            throw new EntityNotFoundException("O serviço não foi encontrado!");
        }

        return serviceMapper.toDto(oService.get());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        log.debug("Removendo um serviço com id {}", id);
        Optional <Service> optionalService = this.serviceService.findById(id);
        ServiceDTO serviceDTO = serviceMapper.toDto(optionalService.get());

        if(!optionalService.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }

        try{
            this.serviceService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Serviço removido com sucesso!");
            return "redirect:/a/servicos";
        }catch (Exception exception) {
            redirectAttributes.addFlashAttribute("msgError", "Serviço não pode ser removido pois já esta sendo utilizado!");
            return "redirect:/a/servicos";
        }
    }

    private ModelAndView errorFowarding(ServiceDTO dto, BindingResult errors) {
        ModelAndView mv = new ModelAndView("admin/service-register");
        mv.addObject("dto", dto);
        mv.addObject("errors", errors.getAllErrors());

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("services", serviceDTOS);

        //carrega a paginação
        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(servicePage);
        mv.addObject("pagination", paginationDTO);

        return mv;
    }
}
