package br.com.tbiazin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "TB_CLIENTE")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
	@SequenceGenerator(name = "cliente_seq", sequenceName = "sq_cliente", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "nome", length = 30, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 30, nullable = false)
	private String cpf;

	@Column(name = "email", length = 30, nullable = false)
	private String email;
	
	@Column(name = "cep", length = 30, nullable = false)
	private String cep;

	@Column(name = "telefone", length = 16, nullable = false)
	private String telefone;

	@Column(name = "rua", length = 30, nullable = false)
	private String rua;

	@Column(name = "numero", nullable = false)
	private Long numero;

	@Column(name = "bairro", length = 30, nullable = false)
	private String bairro;

	@Column(name = "cidade", length = 30, nullable = false)
	private String cidade;

	@Column(name = "uf", length = 30, nullable = false)
	private String uf;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	private List<Venda> vendas;

}
