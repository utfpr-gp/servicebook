package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.name = :name AND c.state = :state")
    Optional<City> findByNameAndState(@Param("name") String name, @Param("state") State state);

    Optional<City> findByName(String name);

    Optional<City> findByIdAndState(Long id, State state);
    Optional<City> findByIdAndState_Id(Long cityId, Long stateId);

}
