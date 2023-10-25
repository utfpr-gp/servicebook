package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

/**
 * Classe que representa uma oferta de serviço para ser vendida na loja de serviços.
 * Este anúncio é criado pelo profissional e pode ser comprado por um cliente.
 * O cliente pode agendar o serviço com o profissional diretamente pela plataforma.
 * O anúncio pode ser de um serviço individual, de serviços diferentes combinados ou de um pacote com quantidade de um único serviço.
 * Esta classe deve ser usada apenas para cadastrar anúncios de pacotes de serviços.
 * Porém, ela pode ser usada para cadastrar anúncios de serviços individuais, mas não é recomendado.
 * Tipos: individual, pacote combinado, pacote simples.
 */
@Data
@Table(name = "professional_service_package_offering")
@NoArgsConstructor
@Entity
public class ProfessionalServicePackageOffering {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo de anúncio.
     * Individual: anúncio de um único serviço.
     * Pacote combinado: anúncio de dois ou mais serviços diferentes.
     * Pacote simples: anúncio de um pacote com quantidade de um único serviço.
     */
    public enum Type {
        INDIVIDUAL,
        COMBINED_PACKAGE,
        SIMPLE_PACKAGE
    }

    /**
     * Tipo de anúncio.
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * Título do anúncio.
     * É usado principalmente para anúncios de pacotes.
     * Quando é tipo individual, o título é o nome do serviço.
     */
    private String name;

    /**
     * Descrição do anúncio.
     * É usado principalmente para anúncios de pacotes.
     * Quando é tipo individual, a descrição é a descrição do serviço.
     */
    private String description;

    /**
     * Preço do pacote de serviços.
     */
    private Long price;

    /**
     * Unidade de preço do serviço.
     */
    private String unit;

    /**
     * Duração do serviço.
     */
    private String duration;

    /**
    * Quantidade, para o pacote de serviços.
    * */
    private Integer amount;

    /**
     * Usuário que criou o anúncio.
     */
    @ManyToOne
    private User user;

    /**
     * Especialidade do anúncio.
     */
    @ManyToOne
    private Expertise expertise;

    /**
     * Serviço cadastrado pelo administrador.
     * O profissional pode customizar a descrição do serviço cadastrado pelo administrador de várias maneiras, dependendo
     * do tipo de particularidade que o seu serviço tem.
     */
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}
