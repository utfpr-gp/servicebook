package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByEmail(String email);

}
