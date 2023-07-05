package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Follows;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowsRepository extends JpaRepository<Follows, Long> {

    /**
     * Retorna todos os clientes que seguem o profissional
     * @param professional
     * @return
     */
    @Query("SELECT f FROM Follows f WHERE f.professional = :professional")
    List<Follows> findFollowsByProfessional(@Param("professional") User professional);

    /**
     * Retorna todos os profissionais favoritos que o cliente segue
     * @param client
     * @return
     */
    @Query("SELECT f FROM Follows f WHERE f.client = :client")
    List<Follows> findFollowsByClient(@Param("client") User client);

    /**
     * Pode ser usado para verificar se o cliente já segue o profissional.
     * @param professional
     * @param client
     * @return
     */
    @Query("SELECT f FROM Follows f WHERE f.professional = :professional and f.client = :client")
    List<Follows> isClientFollowProfessional(@Param("professional") User professional, @Param("client") User client);

    /**
     * Retorna a quantidade de seguidores de um profissional.
     * @param professional
     * @return
     */
    Optional<Long> countByProfessional(User professional);

    /**
     * Retorna a quantidade de profissionais favoritos de um cliente.
     * @param client
     * @return
     */
    public Long countByClient(User client);
}