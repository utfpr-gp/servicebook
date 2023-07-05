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
public class AddressFullDTO implements Serializable {
    @NotBlank(message = "Por favor, insira o nome da rua.")
    private String street;
    private String number;
    @NotBlank(message = "Por favor, insira o CEP.")
    private String postalCode;
    private String neighborhood;
    @NotNull(message = "Por favor, selecione uma cidade.")
    private CityMidDTO city;

}
