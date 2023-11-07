package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    /**
     * Busca uma categoria pelo nome
     *
     * @param name
     * @return
     */
    Optional<Category> findByName(String name);
    List<Category> findAll();


    Page<Category> findAll(Pageable pageable);

    List<Category> findAllById(Iterable<Long> longs);

}

