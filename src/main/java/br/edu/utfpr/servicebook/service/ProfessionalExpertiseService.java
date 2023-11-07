package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
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

    public Optional<ProfessionalExpertise> findByProfessionalAndExpertise(User user, Expertise expertise) {
        return this.professionalExpertiseRepository.findByProfessionalAndExpertise(user, expertise);
    }

    public List<ProfessionalExpertise> findByExpertise(Expertise expertise) {
        return this.professionalExpertiseRepository.findByExpertise(expertise);
    }

    public List<ProfessionalExpertise> findByProfessional(User user) {
        return this.professionalExpertiseRepository.findByProfessional(user);
    }

    public ProfessionalExpertise save(ProfessionalExpertise professionalExpertise) {
        return this.professionalExpertiseRepository.save(professionalExpertise);
    }

    public void delete(ProfessionalExpertisePK professional_id){
        professionalExpertiseRepository.deleteById(professional_id);
    }
}