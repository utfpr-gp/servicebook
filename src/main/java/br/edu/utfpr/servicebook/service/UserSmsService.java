package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.UserSms;
import br.edu.utfpr.servicebook.model.repository.UserSmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSmsService {

    @Autowired
    private UserSmsRepository userSmsRepository;

    public void save(UserSms entity) {
        userSmsRepository.save(entity);
    }

    public void deleteById(Long id) {
        userSmsRepository.deleteById(id);
    }

    public Optional<UserSms> findByPhoneNumber(String phoneNumber) {
        return this.userSmsRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<UserSms> findByCode(String code) {
        return this.userSmsRepository.findByCode(code);
    }

    public Optional<UserSms> findByPhoneNumberAndCode(String phoneNumber, String code) {
        return this.userSmsRepository.findByPhoneNumberAndCode(phoneNumber, code);
    }

}
