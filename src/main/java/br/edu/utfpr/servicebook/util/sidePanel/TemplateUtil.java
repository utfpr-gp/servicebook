package br.edu.utfpr.servicebook.util.sidePanel;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateUtil {

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    /**
     * Retorna os dados do usuário a ser apresentado no menu lateral.
     * @param entity
     * @return
     */
    public UserTemplateInfo getUserInfo(UserDTO entity){
        return new UserTemplateInfo(entity.getId(), entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified(), entity.getFollowingAmount());
    }

    public UserTemplateInfo getUserInfo(User entity){
        return new UserTemplateInfo(entity.getId(), entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified(), 0L);
    }

    /**
     * Retorna os dados estatísticos a serem apresentados no menu lateral para o perfil do profissional.
     * @param oProfessional
     * @param expertiseId
     * @return
     */
    public UserTemplateStatisticDTO getProfessionalStatisticInfo(User oProfessional, Long expertiseId) {

        if (expertiseId == 0L) {
            ProfessionalDTO professional = professionalMapper.toResponseDto(oProfessional);

            return new UserTemplateStatisticDTO(
                    jobContractedService.countByProfessional(oProfessional).orElse(0L),
                    jobContractedService.countRatingByProfessional(oProfessional).orElse(0L),
                    jobContractedService.countCommentsByProfessional(oProfessional).orElse(0L),
                    professional.getRating()
            );
        }

        if (expertiseId < 0) {
            throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
        }

        Optional<Expertise> oExpertise = expertiseService.findById(expertiseId);
        if (!oExpertise.isPresent()) {
            throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
        }

        Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(
                oProfessional,
                oExpertise.get()
        );

        if (!oProfessionalExpertise.isPresent()) {
            throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
        }

        Long totalJobsByExpertise = jobContractedService.countByProfessionalAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);
        Long totalRatingsByExpertise = jobContractedService.countRatingByProfessionalAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);
        Long totalCommentsByExpertise = jobContractedService.countCommentsByProfessionalAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);

        return new UserTemplateStatisticDTO(
                totalJobsByExpertise,
                totalRatingsByExpertise,
                totalCommentsByExpertise,
                oProfessionalExpertise.get().getRating()
        );
    }

    public SidePanelCompanyDTO getCompanyStatisticInfo(User oProfessional, Long expertiseId) {

        if (expertiseId == 0L) {
            ProfessionalDTO professional = professionalMapper.toResponseDto(oProfessional);

            return new SidePanelCompanyDTO(
                    jobContractedService.countByProfessional(oProfessional).orElse(0L),
                    jobContractedService.countRatingByProfessional(oProfessional).orElse(0L),
                    jobContractedService.countCommentsByProfessional(oProfessional).orElse(0L),
                    professional.getRating()
            );
        }

        if (expertiseId < 0) {
            throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
        }

        Optional<Expertise> oExpertise = expertiseService.findById(expertiseId);
        if (!oExpertise.isPresent()) {
            throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
        }

        Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(
                oProfessional,
                oExpertise.get()
        );

        if (!oProfessionalExpertise.isPresent()) {
            throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
        }

        Long totalJobsByExpertise = jobContractedService.countByProfessionalAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);
        Long totalRatingsByExpertise = jobContractedService.countRatingByProfessionalAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);
        Long totalCommentsByExpertise = jobContractedService.countCommentsByProfessionalAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);

        return new SidePanelCompanyDTO(
                totalJobsByExpertise,
                totalRatingsByExpertise,
                totalCommentsByExpertise,
                oProfessionalExpertise.get().getRating()
        );
    }
}