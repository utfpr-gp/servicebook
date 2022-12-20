package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.model.entity.Follows;
import br.edu.utfpr.servicebook.model.entity.Individual;
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
    List<Follows> findFollowsByProfessional(@Param("professional") Individual professional);

    /**
     * Retorna todos os profissionais favoritos que o cliente segue
     * @param client
     * @return
     */
    @Query("SELECT f FROM Follows f WHERE f.client = :client")
    List<Follows> findFollowsByClient(@Param("client") Individual client);

    /**
     * Pode ser usado para verificar se o cliente já segue o profissional.
     * @param professional
     * @param client
     * @return
     */
    @Query("SELECT f FROM Follows f WHERE f.professional = :professional and f.client = :client")
    List<Follows> isClientFollowProfessional(@Param("professional") Individual professional, @Param("client") Individual client);

    /**
     * Retorna a quantidade se seguidores de um profissional.
     * @param professional
     * @return
     */
    Optional<Long> countByProfessional(Individual professional);

    /**
     * Retorna a quantidade de profissionais favoritos de um cliente.
     * @param client
     * @return
     */
    Optional<Long> countByClient(Individual client);
}
