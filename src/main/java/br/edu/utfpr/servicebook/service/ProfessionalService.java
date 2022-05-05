package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.repository.ProfessionalRepository;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    public Professional save(Professional entity){
    public Individual save(Individual entity){

        return individualRepository.save(entity);
    }

    public void delete(Long id){

        individualRepository.deleteById(id);
    }

    public List<Individual> findAll(){

        return this.individualRepository.findAll();
    }
    public Optional<Individual> findById(Long id){

        return this.individualRepository.findById(id);
    }

    public Individual findByEmail(String email){

        return this.individualRepository.findByEmail(email);
    }

    public List<Professional> findDistinctByTermIgnoreCase(String searchTerm){
        return this.professionalRepository.findDistinctByTermIgnoreCase(searchTerm);
    }

    public List<ExpertiseDTO> getExpertises(Professional professional){
        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(professional);

        List<ExpertiseDTO> expertisesDTO = new ArrayList<>();
        for (ProfessionalExpertise professionalExpertise : professionalExpertises) {
            Optional<Expertise> oExpertise = expertiseService.findById(professionalExpertise.getExpertise().getId());

            expertisesDTO.add(expertiseMapper.toDto(oExpertise.get()));
        }

        return expertisesDTO;
    }

    public Optional<Individual> findByCpf(String cpf) {
        return this.individualRepository.findByCpf(cpf);
    }
}