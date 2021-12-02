package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
    Individual findByEmail(String email);

    /**
     * Retorna o usuário por cpf.
     *
     * @param cpf
     * @return Optional<User>
     */
    Optional<Individual> findByCpf(String cpf);
}