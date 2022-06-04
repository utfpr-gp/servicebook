package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertisePK;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalExpertiseService {

    @Autowired
    private ProfessionalExpertiseRepository professionalExpertiseRepository;

    public Optional<Integer> selectRatingByProfessionalAndExpertise(Long professional_id, Long expertise_id) {
        return this.professionalExpertiseRepository.selectRatingByProfessionalAndExpertise(professional_id, expertise_id);
    }

    public Optional<ProfessionalExpertise> findByProfessionalAndExpertise(Individual individual, Expertise expertise) {
        return this.professionalExpertiseRepository.findByProfessionalAndExpertise(individual, expertise);
    }

    public List<ProfessionalExpertise> findByProfessional(Individual individual) {
        return this.professionalExpertiseRepository.findByProfessional(individual);
    }


    public ProfessionalExpertise save(ProfessionalExpertise professionalExpertise) {
        return this.professionalExpertiseRepository.save(professionalExpertise);
    }

    public void delete(ProfessionalExpertisePK professional_id){
        professionalExpertiseRepository.deleteById(professional_id);
    }

}