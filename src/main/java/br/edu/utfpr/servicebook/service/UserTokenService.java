package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.UserToken;
import br.edu.utfpr.servicebook.model.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    public void save(UserToken entity) {
        userTokenRepository.save(entity);
    }

    public void deleteById(Long id) {
        userTokenRepository.deleteById(id);
    }

    public Optional<UserToken> findByEmail(String email) {
        return this.userTokenRepository.findByEmail(email);
    }

}
