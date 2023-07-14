package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalServiceOfferingAdRepository extends JpaRepository<ProfessionalServiceOfferingAd, Long> {


    /**
     * Busca todos anúncios(ProfessionalServiceOfferingAd) de uma dada especialidade(Expertise) e de um dado usuário.
     */
    @Query("SELECT e FROM ProfessionalServiceOfferingAd e WHERE e.user.id = :userId AND e.expertise.id = :expertiseId")
    public List<ProfessionalServiceOfferingAd> findAllByUserIdAndExpertiseId(Long userId, Long expertiseId);

    /**
     * Busca todos anúncios(ProfessionalServiceOfferingAd) de uma dada especialidade(Expertise) e de um dado usuário.
     */
    public List<ProfessionalServiceOfferingAd> findAllByUserAndExpertise(User user, Expertise expertise);

    /**
     * Busca os anúncios de um dado profissional.
     */
    public List<ProfessionalServiceOfferingAd> findAllByUser(User user);
}
