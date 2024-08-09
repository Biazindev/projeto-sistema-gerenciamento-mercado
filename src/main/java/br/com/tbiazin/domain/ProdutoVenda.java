package br.com.tbiazin.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TB_PRODUTO_VENDA")
public class ProdutoVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_venda_seq")
	@SequenceGenerator(name = "produto_venda_seq", sequenceName = "sq_produto_venda", allocationSize = 1)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@JsonIgnoreProperties
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "venda_id", nullable = false)
	private Venda venda;

	@Column(name = "quantidade", nullable = false)
	private int quantidade;

	@Column(name = "preco_unitario", nullable = false)
	private double precoUnitario;

}
