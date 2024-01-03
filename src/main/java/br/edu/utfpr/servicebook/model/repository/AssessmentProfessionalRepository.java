package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentProfessionalRepository extends JpaRepository<AssessmentProfessional, Long> {

    List<AssessmentProfessional> findAssessmentProfessionalById(User user);
}
