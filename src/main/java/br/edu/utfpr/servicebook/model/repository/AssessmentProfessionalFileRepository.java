package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.AssessmentProfessional;
import br.edu.utfpr.servicebook.model.entity.AssessmentProfessionalFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentProfessionalFileRepository extends JpaRepository<AssessmentProfessionalFiles, Long> {
}
