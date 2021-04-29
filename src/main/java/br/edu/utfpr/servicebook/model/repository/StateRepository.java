package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {

}

