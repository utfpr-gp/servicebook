package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
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

    public Optional<ProfessionalExpertise> findByProfessionalAndExpertise(Professional professional, Expertise expertise) {
        return this.professionalExpertiseRepository.findByProfessionalAndExpertise(professional, expertise);
    }

    public List<ProfessionalExpertise> findByProfessional(Professional professional) {
        return this.professionalExpertiseRepository.findByProfessional(professional);
    }


    public ProfessionalExpertise save(ProfessionalExpertise professionalExpertise) {
        return this.professionalExpertiseRepository.save(professionalExpertise);
    }
}