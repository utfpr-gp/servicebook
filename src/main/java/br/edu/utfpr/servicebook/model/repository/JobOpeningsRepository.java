package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.JobOpenings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface JobOpeningsRepository extends JpaRepository<JobOpenings, Long> {


    List<JobOpenings> findAll();
}
