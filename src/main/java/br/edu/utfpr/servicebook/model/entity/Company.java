package br.edu.utfpr.servicebook.model.entity;

import br.edu.utfpr.servicebook.security.ProfileEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"jobRequest", "candidatures"})
@ToString(exclude = {"candidatures", "jobRequest"})

@Table(name = "companies")
@Entity
public class Company extends User {

	private static final long serialVersionUID = 1L;

	private String cnpj;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProfileEnum profile;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Company(String name, String email, String password, String phoneNumber, String cnpj){
		super(name, email, password, phoneNumber);
		setCnpj(cnpj);
	}

	/**
	 * Cadastro parcial usando quando o usuário está cadastrando um pedido de serviço sem estar logado.
	 * Ele passa apenas uns dados principais. Depois ele continua o cadastro.
	 * @param name
	 * @param email
	 * @param phoneNumber
	 * @param cnpj
	 */
	public Company(String name, String email, String phoneNumber, String cnpj){
		super(name, email, phoneNumber);
		setCnpj(cnpj);
	}
}
