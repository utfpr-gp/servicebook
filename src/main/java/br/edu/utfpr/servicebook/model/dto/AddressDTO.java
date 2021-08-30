package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {

    @NotBlank(message = "Rua inválida! Por favor, insira a rua do endereço.", groups = AddressDTO.RequestAddressStreetInfoGroupValidation.class)
    private String street;

    @NotBlank(message = "Número inválido! Por favor, insira o número do endereço.", groups = AddressDTO.RequestAddressNumberInfoGroupValidation.class)
    private String number;

    @NotBlank(message = "CEP inválido! Por favor, insira o CEP do endereço.", groups = AddressDTO.RequestAddressPostalCodeInfoGroupValidation.class)
    private String postalCode;

    @NotBlank(message = "Bairro inválido! Por favor, insira o bairro do endereço.", groups = AddressDTO.RequestAddressNeighborhoodInfoGroupValidation.class)
    private String neighborhood;

    @NotBlank(message = "Cidade Inválida! Por favor, insira a cidade do endereço.", groups = AddressDTO.RequestAddressCityInfoGroupValidation.class)
    private String city;

    @NotBlank(message = "Estado inválido! Por favor, insira o estado do endereço.", groups = AddressDTO.RequestAddressStateInfoGroupValidation.class)
    private String state;

    public interface RequestAddressStreetInfoGroupValidation {

    }

    public interface RequestAddressNumberInfoGroupValidation {

    }

    public interface RequestAddressPostalCodeInfoGroupValidation {

    }

    public interface RequestAddressNeighborhoodInfoGroupValidation {

    }

    public interface RequestAddressCityInfoGroupValidation {

    }

    public interface RequestAddressStateInfoGroupValidation {

    }

}
