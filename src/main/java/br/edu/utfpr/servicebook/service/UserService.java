package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.CompanyProfessional;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import br.edu.utfpr.servicebook.model.repository.UserRepository;
import br.edu.utfpr.servicebook.security.ProfileEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfessionalExpertiseRepository professionalExpertiseRepository;

    public void save(User entity) {
        userRepository.save(entity);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> findByName(String name) {
        return this.userRepository.findByName(name);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return this.userRepository.findByPhoneNumber(phoneNumber);
    }

    public List<User> findProfessionalsNotExist() {
        return this.userRepository.findProfessionals();
    }
    public List<String> findAllByNames() {
        return this.userRepository.findProfessionalsNames();
    }

}
