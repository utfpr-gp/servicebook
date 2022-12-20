package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.model.entity.Follows;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowsRepository extends JpaRepository<Follows, Long> {

//    List<Follows> findByIndividual(Individual individual);


    @Query("SELECT f FROM Follows f WHERE f.professional = :professional")
    List<Follows> findFollowsByProfessional(@Param("professional") Individual professional);

    @Query("SELECT f FROM Follows f WHERE f.client = :client")
    List<Follows> findFollowsByClient(@Param("client") Individual client);
}
//
//    @Query("SELECT f FROM Follows f WHERE f.followed_id = :followed_id")
//    Follow findFollowsByidFollowed(@Param("followed_id") Long followed_id);
//
//    @Query("SELECT f.amount_followers FROM Follow f WHERE f.followed_id = :followed_id")
//    void findPendingAmountFollowersByidFollowed(@Param("followed_id") Long followed_id);
