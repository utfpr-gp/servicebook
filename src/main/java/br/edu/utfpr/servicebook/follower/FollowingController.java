package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.controller.ExpertiseController;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.JobCandidateMapper;
import br.edu.utfpr.servicebook.model.mapper.UserMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.sidePanel.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.sidePanel.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/profissionais-favoritos")
@Controller
public class FollowingController {

    public static final Logger log = LoggerFactory.getLogger(ExpertiseController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    FollowsService followingService;

    @Autowired
    FollowsRepository followingRepository;

    @Autowired
    FollowsMapper followingMapper;

    @Autowired
    JobCandidateService jobCandidateService;

    @Autowired
    JobCandidateMapper jobCandidateMapper;

    @Autowired
    IAuthentication authentication;

    @Autowired
    TemplateUtil templateUtil;

    /**
     * Apresenta a lista de profissionais favoritos de um cliente.
     * @return
     * @throws Exception
     */
    @GetMapping
    public ModelAndView showFavoriteProfessionals() throws Exception {

        ModelAndView mv = new ModelAndView("client/details-follows");

        Optional<User> oIndividual = userService.findByEmail(authentication.getEmail());
        UserDTO clientDTO = userMapper.toDto(oIndividual.get());

        UserTemplateInfo userTemplateInfo = templateUtil.getUserInfo(clientDTO);
        mv.addObject("individualInfo", userTemplateInfo);

        //lista de seguidores
        List<Follows> followingList = followingService.findFollowingByClient(oIndividual.get());

        List<User> oProfessionalList = followingList.stream()
                .map(follows -> {
                    return follows.getProfessional();
                })
                .collect(Collectors.toList());

        mv.addObject("professionals", oProfessionalList);

        return mv;
    }

    /**
     * Trata da solitação via Fetch API para um cliente seguir um profissional.
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> save(@Valid FollowsDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) {

        String currentUserEmail = authentication.getEmail();

        for(FieldError e: errors.getFieldErrors()){
            log.info(e.getField() + " -> " + e.getCode());
        }

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        Follows follows = new Follows(dto.getClient(), dto.getProfessional());
        followingService.save(follows);

        return ResponseEntity.ok().build();
    }

    /**
     * Remove o registro de seguir de um cliente para um profissional
     * @param professionalId
     * @param redirectAttributes
     * @return
     */
    @DeleteMapping("/{professionalId}")
    public ResponseEntity<Void> delete(@PathVariable Long professionalId, RedirectAttributes redirectAttributes) {

        String currentUserEmail = authentication.getEmail();

        Optional<User> oClient = userService.findByEmail(currentUserEmail);
        Optional<User> oProfessional = userService.findById(professionalId);

        if(!oProfessional.isPresent() || !oClient.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        List<Follows> followList = followingService.findFollowProfessionalClient(oProfessional.get(), oClient.get());

        if(followList != null && !followList.isEmpty()){
            followingService.delete(followList.get(0));
        }

        return ResponseEntity.ok().build();
    }
}