package br.edu.utfpr.servicebook.follower;

import org.cloudinary.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {


    @Query("SELECT f FROM Follow f WHERE f.followed_id = :followed_id")
    Follow findFollowsByidFollowed(@Param("followed_id") Long followed_id);

    @Query("SELECT f.amount_followers FROM Follow f WHERE f.followed_id = :followed_id")
    void findPendingAmountFollowersByidFollowed(@Param("followed_id") Long followed_id);
}
