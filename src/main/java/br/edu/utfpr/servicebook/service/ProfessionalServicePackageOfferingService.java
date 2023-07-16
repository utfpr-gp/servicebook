package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServicePackageOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ProfessionalServicePackageOfferingService {

    @Autowired
    private ProfessionalServicePackageOfferingRepository professionalServicePackageOfferingRepository;

    /**
     * Busca todos os anúncios de pacotes de serviços de uma dada especialidade(Expertise) e de um dado usuário.
     * @param userId
     * @param expertiseId
     * @return
     */
    public List<ProfessionalServicePackageOffering> findAllByUserIdAndExpertiseId(Long userId, Long expertiseId){
        return this.professionalServicePackageOfferingRepository.findAllByUserIdAndExpertiseId(userId, expertiseId);
    }

    /**
     * Busca todos os anúncios de pacotes de serviços de uma dada especialidade(Expertise) e de um dado usuário.
     */
    public List<ProfessionalServicePackageOffering> findAllByUserAndExpertise(User user, Expertise expertise){
        return this.professionalServicePackageOfferingRepository.findAllByUserAndExpertise(user, expertise);
    }

    /**
     * Busca os anúncios de pacote de serviços de um dado profissional.
     */
    public List<ProfessionalServicePackageOffering> findAllByUser(User user){
        return this.professionalServicePackageOfferingRepository.findAllByUser(user);
    }
}
