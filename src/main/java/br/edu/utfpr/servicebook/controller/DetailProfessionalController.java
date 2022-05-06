//package br.edu.utfpr.servicebook.controller;
//
//import br.edu.utfpr.servicebook.model.dto.JobContractedDTO;
//import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
//import br.edu.utfpr.servicebook.model.entity.Professional;
//import br.edu.utfpr.servicebook.model.entity.JobContracted;
//import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
//import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
//import br.edu.utfpr.servicebook.service.JobContractedService;
//import br.edu.utfpr.servicebook.util.CurrentUserUtil;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RequestMapping("/detalhes-profissional")
//@Controller
//public class DetailProfessionalController {
//
//    @Autowired
//    private ProfessionalService professionalService;
//
//    @Autowired
//    private JobContractedService jobContractedService;
//
//    @Autowired
//    private ProfessionalMapper professionaMapper;
//
//    @Autowired
//    private JobContractedMapper jobContractedMapper;
//
//    @GetMapping
//    public ModelAndView showForm(){
//        ModelAndView mv = new ModelAndView("client/details-contact");
//
//        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));
//        ProfessionalDTO professionalDTO = professionaMapper.toResponseDto(oProfessional.get());
//        mv.addObject("professional", professionalDTO);
//
//        List<JobContracted> jobContracted = jobContractedService.findByIdProfessional(professionalDTO.getId());
//        List<JobContractedDTO> JobContractedDTO = jobContracted.stream()
//                .map(contracted -> jobContractedMapper.toResponseDto(contracted))
//                .collect(Collectors.toList());
//        mv.addObject("jobContracted", JobContractedDTO);
//
//        return mv;
//    }
//}