package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressFullDTO implements Serializable {

    private String street;
    private String number;
    private String postalCode;
    private String neighborhood;
    private CityMidDTO city;

}
