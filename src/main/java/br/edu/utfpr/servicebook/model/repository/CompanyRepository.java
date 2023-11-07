package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * Retorna o usuário por cnpj.
     *
     * @param cnpj
     * @return Optional<User>
     */
    Optional<Company> findByCnpj(String cnpj);

    /**
     * Retorna o usuário por email.
     *
     * @param email
     * @return Optional<User>
     */
    Optional<Company> findByEmail(String email);

    /**
     * Retorna o usuário por telefone.
     *
     * @param phoneNumber
     * @return Optional<User>
     */
    Optional<Company> findByPhoneNumber(String phoneNumber);

    Optional<Company> findByName(@Param("name") String name);

    @Query("SELECT COUNT(*) FROM Company")
    Long countAll();
}
