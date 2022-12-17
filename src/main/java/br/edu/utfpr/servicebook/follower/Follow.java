package br.edu.utfpr.servicebook.follower;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "follows")
@Entity
@NoArgsConstructor
public class Follow {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "followed_id")
    private Long followed_id;

    //lista de seguidoresJson(id, userName)
    @Column(name = "followersJson")
    private String followersJson;

    @Column(name = "amountFollowers")
    private Long amountFollowers;

    /*
    * (id, idseguido, seguidoresJson(id, userName), QuantSeguidores)*/
}
