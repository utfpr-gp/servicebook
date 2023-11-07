package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findByName(@Param("name") String name);

    /**
     * Retorna o usuário por email.
     *
     * @param email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Retorna o usuário por telefone.
     *
     * @param phoneNumber
     * @return Optional<User>
     */
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT COUNT(DISTINCT pe.professional.id) FROM ProfessionalExpertise pe")
      Long countProfessionals();

    @Query("SELECT COUNT(DISTINCT pe.professional.id) FROM ProfessionalExpertise pe WHERE pe.id = :expertiseId")
    Long countProfessionalByExpertiseId(@Param("expertiseId") Long expertiseId);

    @Query("SELECT COUNT(u) FROM User u WHERE NOT EXISTS (SELECT pe FROM ProfessionalExpertise pe WHERE pe.professional = u)")
    Long countUsersWithoutExpertise();

    /**
     * Retorna os profissionais que possuem a expertise informada.
     * @param expertiseId
     * @return
     */
    @Query("SELECT u FROM User u WHERE EXISTS (SELECT pe FROM ProfessionalExpertise pe WHERE pe.professional = u AND pe.expertise.id = :expertiseId)")
    List<User> findByExpertiseId(@Param("expertiseId") Long expertiseId);

    /**
     * Retorna os profissionais que possuem a expertise informada.
     * @param expertise
     * @return
     */
    @Query("SELECT u FROM User u WHERE EXISTS (SELECT pe FROM ProfessionalExpertise pe WHERE pe.professional = u AND pe.expertise = :expertise)")
    List<User> findByExpertise(Expertise expertise);


}
