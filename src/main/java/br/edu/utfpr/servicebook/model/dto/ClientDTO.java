package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO extends UserDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private Integer rating;

}
