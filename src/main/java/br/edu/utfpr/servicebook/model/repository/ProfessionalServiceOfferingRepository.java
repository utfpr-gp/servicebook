package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalServiceOfferingRepository extends JpaRepository<ProfessionalServiceOffering, Long> {

    /**
     * Busca todas os serviços de um profissional
     * @param user
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUser(User user);

    public List<ProfessionalServiceOffering> findFirst3ByUserAndType(User user, Enum type);
    public List<ProfessionalServiceOffering> findByUserAndType(User user, Enum type);

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

    /**
     * Busca o serviço pelo nome.
     * O nome deve ser único.
     * @param name
     * @return
     */
    @Query("SELECT e FROM ProfessionalServiceOffering e WHERE e.name = :name")
    public Optional<ProfessionalServiceOffering> findProfessionalServiceOfferingByName(String name);

    @Query("SELECT e FROM ProfessionalServiceOffering e WHERE e.service = :id")
    public Optional<ProfessionalServiceOffering> findProfessionalServiceOfferingById(Long id);


    /**
     * Retorna os serviços que o usuário possui.
     * @param user
     * @return
     */
    @Query("SELECT u FROM ProfessionalServiceOfferingAdItem u WHERE EXISTS (SELECT pe FROM ProfessionalServiceOffering pe WHERE pe.user = :user)")
    List<ProfessionalServiceOffering> findProfessionalServiceOfferingAdItemsByUser(User user);

    @Query("SELECT u FROM ProfessionalServiceOffering u JOIN ProfessionalServiceOfferingAdItem r WHERE r.professionalServiceOffering.id = u.id")
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingAdItemsByUserJoin(User user);

    @Query("SELECT u FROM ProfessionalServiceOfferingAdItem u JOIN FETCH u.professionalServicePackageOffering")
    List<ProfessionalServiceOffering> findDistinctProfessionalServiceOfferings();

}
