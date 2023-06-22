package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Category;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    /**
     * Busca um serviço pelo nome
     *
     * @param name
     * @return
     */
    Optional<Service> findByName(String name);
    List<Service> findAll();


    Page<Service> findAll(Pageable pageable);

    List<Service> findAllById(Iterable<Long> longs);

    @Query("SELECT e FROM Service e WHERE e.name = :name AND e.expertise = :expertise")
    Optional<Service> findByNameAndExpertise(@Param("name") String name, @Param("expertise") Expertise expertise);

}
