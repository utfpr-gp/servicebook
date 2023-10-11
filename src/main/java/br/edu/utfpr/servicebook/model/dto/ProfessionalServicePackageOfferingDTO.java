package br.edu.utfpr.servicebook.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProfessionalServicePackageOfferingDTO {
    private Long id;

    public enum Type {
        INDIVIDUAL,
        COMBINED_PACKAGE,
        SIMPLE_PACKAGE
    }

    private ProfessionalServiceOfferingDTO.Type type;
    /**
     * Nome do serviço.
     * O profissional pode customizar o nome do serviço cadastrado pelo administrador de várias maneiras, dependendo
     * do tipo de particularidade que o seu serviço tem.
     * Caso ele não especifique o nome do serviço, o nome do serviço cadastrado pelo administrador será usado.
     */
    private String name;

    /**
     * Descrição do serviço em primeira pessoa.
     * O profissional pode customizar a descrição do serviço cadastrado pelo administrador de várias maneiras, dependendo
     * do tipo de particularidade que o seu serviço tem.
     * Caso ele não especifique a descrição do serviço, a descrição do serviço cadastrado pelo administrador será usada.
     */
    private String description;

    /**
     * Unidade de preço do serviço.
     */
    private String unit;

    /**
     * Duração do serviço.
     */
    private String duration;

    private Integer amount;

    /**
     * Serviço cadastrado pelo administrador.
     * O profissional pode customizar a descrição do serviço cadastrado pelo administrador de várias maneiras, dependendo
     * do tipo de particularidade que o seu serviço tem.
     */
    private ServiceDTO service;

    private Long serviceId;
    private Long price;

    /**
     * Profissional que oferece o serviço.
     */
    private UserDTO user;

    private Long userId;
}
