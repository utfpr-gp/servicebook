package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertisePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessionalExpertiseRepository extends JpaRepository<ProfessionalExpertise, ProfessionalExpertisePK> {
    /**
     * Retorna uma lista de especialidades de um profissional
     * @param professional
     * @return
     */
    List<ProfessionalExpertise> findByProfessional(Professional professional);
}