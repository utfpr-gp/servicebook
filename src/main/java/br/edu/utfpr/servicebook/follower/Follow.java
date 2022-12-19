package br.edu.utfpr.servicebook.follower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "follows")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follow {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "followed_id")
    private Long followed_id;

    //lista de seguidoresJson(id, userName)
    @Column(name = "followers_json")
    private String followers_json;

    @Column(name = "amount_followers")
    private Integer amount_followers;

    public Follow(Long followed_id, String followersJson) {
        setFollowed_id(followed_id);
        setFollowers_json(followersJson);
    }

    public Follow(Long followed_id, String followersJson, Integer amount_followers) {
        setFollowed_id(followed_id);
        setFollowers_json(followersJson);
        setAmount_followers(amount_followers);
    }

}
