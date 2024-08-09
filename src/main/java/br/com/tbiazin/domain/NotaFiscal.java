package br.com.tbiazin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TB_NOTA_FISCAL")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nota_fiscal_seq")
    @SequenceGenerator(name = "nota_fiscal_seq", sequenceName = "sq_nota_fiscal", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoNotaFiscal> produtosNotaFiscal;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "natureza_operacao", nullable = false)
    private String naturezaOperacao;

    @Column(name = "forma_pagamento", nullable = false)
    private String formaPagamento;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "finalidade_emissao", nullable = false)
    private String finalidadeEmissao;

    @Column(name = "cnpj_emitente", nullable = false)
    private String cnpjEmitente;

    @Column(name = "nome_emitente", nullable = false)
    private String nomeEmitente;

    @Column(name = "nome_fantasia_emitente", nullable = false)
    private String nomeFantasiaEmitente;

    @Column(name = "logradouro_emitente", nullable = false)
    private String logradouroEmitente;

    @Column(name = "numero_emitente", nullable = false)
    private String numeroEmitente;

    @Column(name = "bairro_emitente", nullable = false)
    private String bairroEmitente;

    @Column(name = "municipio_emitente", nullable = false)
    private String municipioEmitente;

    @Column(name = "uf_emitente", nullable = false)
    private String ufEmitente;

    @Column(name = "cep_emitente", nullable = false)
    private String cepEmitente;

    @Column(name = "telefone_emitente", nullable = false)
    private String telefoneEmitente;

    @Column(name = "inscricao_estadual_emitente", nullable = false)
    private String inscricaoEstadualEmitente;

    @Column(name = "valor_frete", nullable = false)
    private double valorFrete;

    @Column(name = "valor_seguro", nullable = false)
    private double valorSeguro;

    @Column(name = "valor_desconto", nullable = false)
    private double valorDesconto;

    @Column(name = "valor_ipi", nullable = false)
    private double valorIpi;

    @Column(name = "modalidade_frete", nullable = false)
    private String modalidadeFrete;

    @Column(name = "icms_base_calculo", nullable = false)
    private double icmsBaseCalculo;

    @Column(name = "icms_valor_total", nullable = false)
    private double icmsValorTotal;

    @Column(name = "icms_base_calculo_st", nullable = false)
    private double icmsBaseCalculoSt;

    @Column(name = "icms_valor_total_st", nullable = false)
    private double icmsValorTotalSt;

    @Column(name = "icms_modalidade_base_calculo", nullable = false)
    private String icmsModalidadeBaseCalculo;

    @Column(name = "icms_valor", nullable = false)
    private double icmsValor;

    @Column(name = "pis_valor_total", nullable = false)
    private double pisValorTotal;

    @Column(name = "cofins_valor_total", nullable = false)
    private double cofinsValorTotal;

    @Column(name = "valor_outras_despesas", nullable = false)
    private double valorOutrasDespesas;

    @Column(name = "valor_total_tributos", nullable = false)
    private double valorTotalTributos;

    @Column(name = "chave_acesso", nullable = false)
    private String chaveAcesso;

    @Column(name = "protocolo_autorizacao", nullable = false)
    private String protocoloAutorizacao;
}
