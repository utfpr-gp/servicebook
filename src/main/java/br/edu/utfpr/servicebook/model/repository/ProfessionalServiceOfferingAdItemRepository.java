package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.ProfessionalServicePackageOffering;
import br.edu.utfpr.servicebook.model.entity.ProfessionalServiceOfferingAdItem;
import br.edu.utfpr.servicebook.model.entity.ProfessionalServiceOfferingAdItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalServiceOfferingAdItemRepository extends JpaRepository<ProfessionalServiceOfferingAdItem, ProfessionalServiceOfferingAdItemPK> {

    /**
     * Busca todos os itens de um anúncio de um profissional.
     * É util para pegar os ProfessionalServiceOffering de um anúncio, principalmente quando é do tipo pacote.
     */
    List<ProfessionalServiceOfferingAdItem> findAllByProfessionalServicePackageOffering(ProfessionalServicePackageOffering professionalServicePackageOffering);






}
