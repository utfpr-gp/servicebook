package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalServiceOfferingRepository extends JpaRepository<ProfessionalServiceOffering, Long> {

    /**
     * Busca todas os serviços de um profissional
     * @param user
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUser(User user);


    /**
     * Busca todas os serviços de um profissional
     * @param id
     * @return
     */
    @Query("SELECT e FROM ProfessionalServiceOffering e WHERE e.user.id = :id")
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserId(Long id);

    /**
     * Busca todos os serviços de um profissional que é especialidade do serviço cadastrado pelo administrador.
     * @param service
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByService(Service service);

    /**
     * Busca todas os serviços de um profissional que é especialidade do serviço cadastrado pelo administrador.
     */
    @Query("SELECT e FROM ProfessionalServiceOffering e WHERE e.service.id = :id")
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByServiceId(Long id);

    /**
     * Busca todas os serviços de um profissional e especialidade
     */
    @Query("SELECT e FROM ProfessionalServiceOffering e WHERE e.user.id = :userId AND e.service.expertise.id = :expertiseId")
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserAndExpertise(Long userId, Long expertiseId);

    /**
     * Busca todos os serviços de um profissional e especialidade
     * @param user
     * @param expertise
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserAndService_Expertise(User user, Expertise expertise);

}
