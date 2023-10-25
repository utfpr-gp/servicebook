package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalServicePackageOfferingRepository extends JpaRepository<ProfessionalServicePackageOffering, Long> {

    /**
     * Busca todos os anúncios de pacote de serviços de uma dada especialidade(Expertise) e de um dado usuário.
     */
    @Query("SELECT e FROM ProfessionalServicePackageOffering e WHERE e.user.id = :userId AND e.expertise.id = :expertiseId")
    public List<ProfessionalServicePackageOffering> findAllByUserIdAndExpertiseId(Long userId, Long expertiseId);

    /**
     * Busca todos anúncios(ProfessionalServiceOfferingAd) de uma dada especialidade(Expertise) e de um dado usuário.
     */
    public List<ProfessionalServicePackageOffering> findAllByUserAndExpertise(User user, Expertise expertise);

    /**
     * Busca os anúncios de um dado profissional.
     */
    public List<ProfessionalServicePackageOffering> findFirst3ByUserAndType(User user, Enum type);

    public List<ProfessionalServicePackageOffering> findByUserAndType(User user, Enum type);

    @Query("SELECT po FROM ProfessionalServiceOfferingAdItem poa INNER JOIN ProfessionalServiceOffering po ON  poa.professionalServiceOffering = po WHERE po.user = :userId")
    public List<ProfessionalServiceOfferingAdItem> findAllByCombinedAndItems(User userId);
}
