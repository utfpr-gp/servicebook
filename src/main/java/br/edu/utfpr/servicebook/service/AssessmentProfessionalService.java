package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.AssessmentProfessional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertisePK;
import br.edu.utfpr.servicebook.model.repository.AssessmentProfessionalRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssessmentProfessionalService {

    @Autowired
    private AssessmentProfessionalRepository assessmentProfessionalRepository;

    public AssessmentProfessional save(AssessmentProfessional assessmentProfessional) {
        return this.assessmentProfessionalRepository.save(assessmentProfessional);
    }

    public Optional<AssessmentProfessional> findById(Long assessmentProfessional){
        return this.assessmentProfessionalRepository.findById(assessmentProfessional);
    }

}
