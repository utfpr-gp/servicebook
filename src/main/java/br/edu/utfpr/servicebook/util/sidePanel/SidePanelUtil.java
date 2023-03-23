package br.edu.utfpr.servicebook.util.sidePanel;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.CompanyDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.CompanyMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.CompanyExpertiseService;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SidePanelUtil {

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private CompanyExpertiseService companyExpertiseService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private CompanyMapper companyMapper;
    /**
     * Retorna os dados do usuário a ser apresentado no menu lateral.
     * @param entity
     * @return
     */
    public SidePanelIndividualDTO getIndividualInfo(IndividualDTO entity){
        return new SidePanelIndividualDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified(), entity.getFollowingAmount());
    }

    public SidePanelIndividualDTO getIndividualInfo(Individual entity){
        return new SidePanelIndividualDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified(), 0L);
    }

    public SidePanelCompanyDTO getCompanyInfo(CompanyDTO entity){
        return new SidePanelCompanyDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified(), 0L);
    }

    public SidePanelCompanyDTO getCompanyInfo(Company entity){
        return new SidePanelCompanyDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified(), 0L);
    }
    /**
     * Retorna os dados estatísticos a serem apresentados no menu lateral para o perfil do profissional.
     * @param oProfessional
     * @param expertiseId
     * @return
     */
    public SidePanelStatisticsDTO getProfessionalStatisticInfo(Individual oProfessional, Long expertiseId) {

        if (expertiseId == 0L) {
            ProfessionalDTO professional = professionalMapper.toResponseDto(oProfessional);

            return new SidePanelStatisticsDTO(
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

        return new SidePanelStatisticsDTO(
            totalJobsByExpertise,
            totalRatingsByExpertise,
            totalCommentsByExpertise,
            oProfessionalExpertise.get().getRating()
        );
    }

    public SidePanelStatisticsDTO getCompanyStatisticInfo(Company oProfessional, Long expertiseId) {

        if (expertiseId == 0L) {
            CompanyDTO professional = companyMapper.toResponseDto(oProfessional);

            return new SidePanelStatisticsDTO(
                    jobContractedService.countByCompany(oProfessional).orElse(0L),
                    jobContractedService.countRatingByCompany(oProfessional).orElse(0L),
                    jobContractedService.countCommentsByCompany(oProfessional).orElse(0L),
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

        Optional<CompanyExpertise> oProfessionalExpertise = companyExpertiseService.findByCompanyAndExpertise(
                oProfessional,
                oExpertise.get()
        );

        if (!oProfessionalExpertise.isPresent()) {
            throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
        }

        Long totalJobsByExpertise = jobContractedService.countByCompanyAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);
        Long totalRatingsByExpertise = jobContractedService.countRatingByCompanyAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);
        Long totalCommentsByExpertise = jobContractedService.countCommentsByCompanyAndJobRequest_Expertise(
                oProfessional, oExpertise.get()).orElse(0L);

        return new SidePanelStatisticsDTO(
                totalJobsByExpertise,
                totalRatingsByExpertise,
                totalCommentsByExpertise,
                oProfessionalExpertise.get().getRating()
        );
    }
}
