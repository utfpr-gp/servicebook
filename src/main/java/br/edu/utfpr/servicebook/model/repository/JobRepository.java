package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Busca todas as vagas
     * @return
     */
    List<Job> findAll();

    /**
     * Busca todas as vagas de uma empresa
     * @param id
     * @return
     */
    Page<Job> findByCompanyId(Long id, Pageable pageable);

    /**
     * Busca todas as vagas de uma empresa e de uma área de atuação
     * @param company
     * @param expertise
     * @return
     */
    Page<Job> findByCompanyAndExpertise(Company company, Expertise expertise, Pageable pageable);

    /**
     * Busca todas as vagas de uma empresa e de uma área de atuação
     * @param companyId
     * @param expertiseId
     * @return
     */
    Page<Job> findByCompanyIdAndExpertiseId(Long companyId, Long expertiseId, Pageable pageable);
}
