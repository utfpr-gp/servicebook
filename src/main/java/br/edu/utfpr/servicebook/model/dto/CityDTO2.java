package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO2 {
    private Long id;

    private String name;

    private State state;

}
