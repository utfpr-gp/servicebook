package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

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

    public Long countProfessionals(){
        return this.userRepository.countProfessionals();
    }

    public Long countUsersWithoutExpertise(){
        return this.userRepository.countUsersWithoutExpertise();
    }

    public Long countProfessionalsByExpertise(Long expertiseId){
        return this.userRepository.countProfessionalByExpertiseId(expertiseId);
    }

    /**
     * Retorna uma lista de ExpertiseDTOs de um profissional
     * @param professional
     * @return
     */
    public List<ExpertiseDTO> getExpertiseDTOs(User professional){
        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        List<ExpertiseDTO> expertiseDTOs = new ArrayList<>();
        for (ProfessionalExpertise professionalExpertise : professionalExpertises) {
            Expertise expertise = professionalExpertise.getExpertise();

            expertiseDTOs.add(expertiseMapper.toDto(expertise));
        }

        return expertiseDTOs;
    }
}
