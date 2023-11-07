package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.Individual;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowsDTO implements Serializable {

    @NonNull
    private Individual professional;

    @NonNull
    private Individual client;

}
