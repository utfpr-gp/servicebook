package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDTO implements Serializable {

    @NotBlank(message = "Rua inválida! Por favor, insira a rua do endereço.")
    private String street;

    private String number;

    @NotBlank(message = "CEP inválido! Por favor, insira o CEP do endereço.")
    private String postalCode;

    @NotBlank(message = "Bairro inválido! Por favor, insira o bairro do endereço.")
    private String neighborhood;

    @NotNull(message = "Cidade Inválida! Por favor, insira a cidade do endereço.")
    private Long city;

    @NotNull(message = "Estado inválido! Por favor, insira o estado do endereço.")
    private Long state;


}
