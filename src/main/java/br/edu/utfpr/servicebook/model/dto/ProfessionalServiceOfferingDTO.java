package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.Service;
import br.edu.utfpr.servicebook.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Classe que representa um serviço adicionado por um profissional ao seu portfólio.
 * Entre os serviços cadastrados pelo administrador, ele escolhe quais ele oferece.
 * Mesmo que o serviço cadastrado pelo administrador tenha uma descrição, ele pode customizar a descrição do serviço que ele oferece.
 * Ele pode ter várias customizações para um mesmo serviço cadastrado pelo Administrador.
 */
@Data
@NoArgsConstructor
public class ProfessionalServiceOfferingDTO {

    private Long id;

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
     * Serviço cadastrado pelo administrador.
     * O profissional pode customizar a descrição do serviço cadastrado pelo administrador de várias maneiras, dependendo
     * do tipo de particularidade que o seu serviço tem.
     */
    private ServiceDTO service;

    private Long serviceId;
    /**
     * Preço do serviço individual.
     */
    private Long price;
    /**
     * Profissional que oferece o serviço.
     */
    private UserDTO user;

    private Long userId;
}
