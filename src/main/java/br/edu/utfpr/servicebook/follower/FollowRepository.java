package br.edu.utfpr.servicebook.follower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {


    @Query("SELECT f FROM Follow f WHERE f.followed_id = :followed_id")
    Optional<Follow> findPendingFollowsByidFollowed(@Param("followed_id") Long followed_id);

    @Query("SELECT f.amount_followers FROM Follow f WHERE f.followed_id = :followed_id")
    void findPendingAmountFollowersByidFollowed(@Param("followed_id") Long followed_id);

    @Modifying
    @Transactional
    @Query("update Follow f set f.followers_json = :followers_json where f.followed_id = :followed_id")
    void updateFollowById(@Param("followers_json") String followers_json, @Param("followed_id") Long followed_id);


    //metodo para pegar lista de seguidos ok
    //metodo para seguir ok
    //metodo para deseguir ok o mesmo de seguir pois e um lista json logica fia no service
    //metodo quantidade de seguidores ok


}
