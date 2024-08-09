package br.com.tbiazin.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.tbiazin.Util.TipoDePagamentoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "TB_VENDA")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq")
	@SequenceGenerator(name = "venda_seq", sequenceName = "sq_venda", allocationSize = 1)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "data_venda", nullable = false)
	private LocalDate dataVenda;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "pdv_id", nullable = false)
	private PDV pdv;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProdutoVenda> produtosVenda;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pagamento", nullable = false)
	private TipoDePagamentoEnum tipoPagamento;

	@Column(name = "valor_total", nullable = false)
	private double valorTotal;
	
}
