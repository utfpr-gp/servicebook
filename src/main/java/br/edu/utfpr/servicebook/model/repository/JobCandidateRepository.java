package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, JobCandidatePK> {

    List<JobCandidate> findById_Professional_Id(Long id);
    List<JobCandidate> findById_Professional(Professional professional);
    List<JobCandidate> findById_ProfessionalAndChosenByBudget(Professional professional, boolean chosen);
}