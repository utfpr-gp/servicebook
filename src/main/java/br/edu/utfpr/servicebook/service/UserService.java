package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.repository.UserRepository;
import br.edu.utfpr.servicebook.security.IAuthentication;
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
    private IAuthentication authentication;

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

    public List<User> findProfessionalsByExpertiseId(Long expertiseId) {
        return this.userRepository.findByExpertiseId(expertiseId);
    }

    public List<User> findByExpertise(Expertise expertise) {
        return this.userRepository.findByExpertise(expertise);
    }

    public Long countProfessionals(){
        return this.userRepository.countProfessionals();
    }

    public Long countUsersWithoutExpertise(){
        return this.userRepository.countUsersWithoutExpertise();
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

    /**
     * Retorna uma lista de Expertise de um profissional
     * @param professional
     * @return
     */
    public List<Expertise> getExpertises(User professional){
        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        List<Expertise> expertises = new ArrayList<>();
        for (ProfessionalExpertise professionalExpertise : professionalExpertises) {
            Expertise expertise = professionalExpertise.getExpertise();
            expertises.add(expertise);
        }

        return expertises;
    }

    /**
     * Retorna o total de usuários.
     * @return
     */
    public Long countAll(){
        return this.userRepository.count();
    }

    /**
     * Retorna o usuário autenticado ou nulo.
     * @return
     */
    public User getAuthenticated(){
        Optional<User> oUser = findByEmail(authentication.getEmail());

        if (oUser.isPresent()) {
            return oUser.get();
        }

        return null;
    }
}
