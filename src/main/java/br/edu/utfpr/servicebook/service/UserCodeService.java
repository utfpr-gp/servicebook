package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.repository.UserCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCodeService {

    @Autowired
    private UserCodeRepository userCodeRepository;

    public void save(UserCode entity) {
        userCodeRepository.save(entity);
    }

    public void deleteById(Long id) {
        userCodeRepository.deleteById(id);
    }

    public Optional<UserCode> findByEmail(String email) {
        return this.userCodeRepository.findByEmail(email);
    }

    public Optional<UserCode> findByCode(String code) {
        return this.userCodeRepository.findByCode(code);
    }

    public Optional<UserCode> findByEmailAndCode(String email, String code) {
        return this.userCodeRepository.findByEmailAndCode(email, code);
    }

    public void deleteAll() {
        userCodeRepository.deleteAll();
    }
}
