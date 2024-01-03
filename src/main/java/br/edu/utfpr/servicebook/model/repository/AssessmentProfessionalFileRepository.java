package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.AssessmentProfessional;
import br.edu.utfpr.servicebook.model.entity.AssessmentProfessionalFiles;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentProfessionalFileRepository extends JpaRepository<AssessmentProfessionalFiles, Long> {
}
