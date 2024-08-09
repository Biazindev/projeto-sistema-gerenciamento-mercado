package br.com.tbiazin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_fornecedor")
@Entity
public class Fornecedor {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fornecedor_seq")
	@SequenceGenerator(name = "cliente_seq", sequenceName = "sq_fornecedor", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, unique = true)
	private Long Id;
	
	@Column(name = "razao_social", length = 30, nullable = false)
	private String razaoSocial;
	
	@Column(name = "inscricao_estadual", length = 30, nullable = false)
	private String iscricaoEstadual;
	
	@Column(name = "cnpj", length = 30, nullable = false)
	private String cnpj;
	
	@Column(name = "rua", length = 30, nullable = false)
	private String rua;
	
	@Column(name = "numero", length = 30, nullable = false)
	private long numero;
	
	@Column(name = "bairro", length = 30, nullable = false)

	private String bairro;
	
	@Column(name = "uf", length = 30, nullable = false)
	private String uf;
	
	@Column(name = "telefone", length = 30, nullable = false)
	private String telefone;
	
	@Column(name = "email", length = 30, nullable = false)
	private String email;
	
}
