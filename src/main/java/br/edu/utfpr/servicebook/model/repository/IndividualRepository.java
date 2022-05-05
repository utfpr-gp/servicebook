package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Long> {


    /**
     * Retorna o usuário por cpf.
     *
     * @param cpf
     * @return Optional<User>
     */
    Optional<Individual> findByCpf(String cpf);


    /**
     * Retorna o usuário por email.
     *
     * @param email
     * @return Optional<User>
     */
    Optional<Individual> findByEmail(String email);

    /**
     * Retorna o usuário por telefone.
     *
     * @param phoneNumber
     * @return Optional<User>
     */
    Optional<Individual> findByPhoneNumber(String phoneNumber);

    Optional<Individual> findByName(@Param("name") String name);
}