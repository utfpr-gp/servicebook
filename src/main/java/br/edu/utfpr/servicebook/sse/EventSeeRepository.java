package br.edu.utfpr.servicebook.sse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventSeeRepository extends JpaRepository<EventSSE, Long> {

    /**
     * Busca uma lista de notificações a serem enviadas a um dado usuário, mas apenas
     * as notificações que ainda não foram lidas por ele.
     * @param toEmail
     * @return
     */
    @Query("SELECT e FROM EventSSE e WHERE e.toEmail = :toEmail and e.readStatus = false")
    List<EventSSE> findPendingEventsByEmail(@Param("toEmail") String toEmail);

    /**
     * Marca a notificação como lida para o id.
     * Considera-se que uma notificação tem id único e sendo referida a um usuário.
     * @param id
     */
    @Modifying
    @Transactional
    @Query("update EventSSE e set e.readStatus = true where e.id = :id")
    void modifyStatusById(@Param("id") Long id);

}
