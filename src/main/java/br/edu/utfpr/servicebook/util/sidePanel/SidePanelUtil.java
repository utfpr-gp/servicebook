package br.edu.utfpr.servicebook.util.sidePanel;

import br.edu.utfpr.servicebook.model.dto.IndividualDTO;

import java.util.Optional;

public class SidePanelUtil {

    public static SidePanelIndividualDTO getSidePanelDTO(IndividualDTO entity){
        return new SidePanelIndividualDTO(entity.getName(), entity.getDescription(), entity.getRating(), entity.getProfilePicture(), entity.isPhoneVerified(), entity.isEmailVerified(), entity.isProfileVerified());
    }
    public static SidePanelItensDTO sidePanelItensDTO(Optional<Long> jobs, Optional<Long> comments, Optional<Long> ratings){
        return new SidePanelItensDTO(jobs.get(), comments.get(), ratings.get());
    }

    public static SidePanelProfessionalExpertiseRatingDTO sidePanelProfessionalExpertiseRatingDTO(Optional<Integer> selectRatingByProfessionalAndExpertise) {
        return new SidePanelProfessionalExpertiseRatingDTO(selectRatingByProfessionalAndExpertise.get());
    }
}
