package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.UserSms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSmsRepository extends JpaRepository<UserSms, Long> {

    Optional<UserSms> findByPhoneNumber(String email);

    Optional<UserSms> findByCode(String code);

    Optional<UserSms> findByPhoneNumberAndCode(String email, String code);

}
