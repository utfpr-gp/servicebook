package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.CompanyProfessional;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
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

    @Query("SELECT  e FROM User e WHERE e.profile = 'ROLE_USER'")
    List<User> findProfessionals();
    List<User> findByNameContainingIgnoreCase(String name);

    @Query("SELECT  e.name  FROM User e")
    List<String> findProfessionalsNames();

    @Query("SELECT COUNT(DISTINCT pe.professional.id) FROM ProfessionalExpertise pe")
      Long countProfessionals();

    @Query("SELECT COUNT(u) FROM User u WHERE NOT EXISTS (SELECT pe FROM ProfessionalExpertise pe WHERE pe.professional = u)")
    Long countUsersWithoutExpertise();

}
