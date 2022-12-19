package br.edu.utfpr.servicebook.follower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowDTO implements Serializable {

    private Long id;

    private Long followed_id;

    private String followersJson;

    private Long amountFollowers;

}
