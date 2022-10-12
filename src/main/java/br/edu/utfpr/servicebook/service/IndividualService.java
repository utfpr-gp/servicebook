package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.repository.IndividualRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import br.edu.utfpr.servicebook.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IndividualService {

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private ProfessionalExpertiseRepository professionalExpertiseRepository;
    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    public void save(Individual entity) {
        individualRepository.save(entity);
    }

    public void delete(Long id) {
        individualRepository.deleteById(id);
    }

    public List<Individual> findAll() {
        return this.individualRepository.findAll();
    }

    public Optional<Individual> findById(Long id) {
        return this.individualRepository.findById(id);
    }

    public Optional<Individual> findByName(String name) {
        return this.individualRepository.findByName(name);
    }

    public Optional<Individual> findByCpf(String cpf) {
        return this.individualRepository.findByCpf(cpf);
    }

    public Optional<Individual> findByEmail(String email) {
        return this.individualRepository.findByEmail(email);
    }

    public Optional<Individual> findByPhoneNumber(String phoneNumber) {
        return this.individualRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Individual> findDistinctByTermIgnoreCase(String searchTerm){
        return this.individualRepository.findDistinctByTermIgnoreCase(searchTerm);
    }

    public Page<Individual> findDistinctByTermIgnoreCaseWithPagination(String searchTerm, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return this.individualRepository.findDistinctByTermIgnoreCaseWithPagination(searchTerm, pageRequest);
    }

    public List<ExpertiseDTO> getExpertises(Individual professional){
        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        List<ExpertiseDTO> expertisesDTO = new ArrayList<>();
        for (ProfessionalExpertise professionalExpertise : professionalExpertises) {
            Optional<Expertise> oExpertise = expertiseService.findById(professionalExpertise.getExpertise().getId());

            expertisesDTO.add(expertiseMapper.toDto(oExpertise.get()));
        }

        return expertisesDTO;
    }

    @Transactional
    public void saveExpertisesIndividual(Individual individual, ProfessionalExpertise professionalExpertise) {
        individualRepository.save(individual);
        professionalExpertiseRepository.save(professionalExpertise);
    }

}