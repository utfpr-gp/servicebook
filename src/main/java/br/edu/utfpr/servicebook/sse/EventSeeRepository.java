package br.edu.utfpr.servicebook.sse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventSeeRepository extends JpaRepository<EventSse, Long> {

//    @Query("SELECT e FROM EventSse e WHERE e.toUserEmail = :toUserEmail")
//    List<EventSse> findByEmail(@Param("toUserEmail") String toUserEmail);


    @Query("SELECT e FROM EventSse e WHERE e.toUserEmail = :toUserEmail and e.readStatus = false")
    List<EventSse> findByEmail(@Param("toUserEmail") String toUserEmail);

    //fazer metodo que acessa a notificação pelo parametro id e modifica a coluna lido
    @Modifying
    @Transactional
    @Query("update EventSse e set e.readStatus =: true where e.id = :id")
    void modifyStatusById(@Param("id") Long id);

}
