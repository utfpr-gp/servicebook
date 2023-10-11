package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalServiceOfferingAdItemRepository extends JpaRepository<ProfessionalServiceOfferingAdItem, ProfessionalServiceOfferingAdItemPK> {

    /**
     * Busca todos os itens de um anúncio de um profissional.
     * É util para pegar os ProfessionalServiceOffering de um anúncio, principalmente quando é do tipo pacote.
     */
    List<ProfessionalServiceOfferingAdItem> findAllByProfessionalServicePackageOffering(ProfessionalServicePackageOffering professionalServicePackageOffering);

    /**
     * Retorna os serviços que o usuário possui.
     * @param user
     * @return
     */
//    @Query("SELECT u FROM ProfessionalServiceOffering u WHERE EXISTS (SELECT pe FROM ProfessionalServicePackageOffering pe WHERE pe.user = :user)")
//    List<ProfessionalServiceOffering> findProfessionalServiceOfferingAdItemsByUser(User user);

//    @Query("SELECT u FROM ProfessionalServiceOffering u JOIN u.packageOfferings pe WHERE pe.user = :user")
//    List<ProfessionalServiceOffering> findProfessionalServiceOfferingAdItemsByUser(User user);
//

    @Query("SELECT u FROM ProfessionalServiceOfferingAdItem u JOIN u.professionalServiceOffering pso JOIN u.professionalServicePackageOffering jspo")
    List<ProfessionalServiceOfferingAdItem> findProfessionalServiceOfferingAdItemsByUserJoin(User user);

    @Query("SELECT psa FROM ProfessionalServiceOfferingAdItem psa " +
            "JOIN FETCH  psa.professionalServiceOffering pso")
    List<ProfessionalServiceOfferingAdItem> findProfessionalServiceOfferingAdItemsWithRelatedEntities();

}
