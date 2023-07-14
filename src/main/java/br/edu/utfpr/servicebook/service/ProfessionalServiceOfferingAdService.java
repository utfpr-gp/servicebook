package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServiceOfferingAdRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ProfessionalServiceOfferingAdService {

    @Autowired
    private ProfessionalServiceOfferingAdRepository professionalServiceOfferingAdRepository;

    public List<ProfessionalServiceOfferingAd> findAllByUserIdAndExpertiseId(Long userId, Long expertiseId){
        return this.professionalServiceOfferingAdRepository.findAllByUserIdAndExpertiseId(userId, expertiseId);
    }

    /**
     * Busca todos anúncios(ProfessionalServiceOfferingAd) de uma dada especialidade(Expertise) e de um dado usuário.
     */
    public List<ProfessionalServiceOfferingAd> findAllByUserAndExpertise(User user, Expertise expertise){
        return this.professionalServiceOfferingAdRepository.findAllByUserAndExpertise(user, expertise);
    }

    /**
     * Busca os anúncios de um dado profissional.
     */
    public List<ProfessionalServiceOfferingAd> findAllByUser(User user){
        return this.professionalServiceOfferingAdRepository.findAllByUser(user);
    }
}
