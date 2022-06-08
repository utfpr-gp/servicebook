package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCodeRepository extends JpaRepository<UserCode, Long> {

    Optional<UserCode> findByEmail(String email);

    Optional<UserCode> findByCode(String code);

    Optional<UserCode> findByEmailAndCode(String email, String code);

    void deleteAll();

}
