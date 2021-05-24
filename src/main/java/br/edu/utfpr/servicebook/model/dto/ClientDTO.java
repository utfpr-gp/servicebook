package br.edu.utfpr.servicebook.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String number;
    private String address;

}
