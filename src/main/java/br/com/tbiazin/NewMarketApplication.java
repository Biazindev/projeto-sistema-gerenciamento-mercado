package br.com.tbiazin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.tbiazin.domain.Cliente;
import br.com.tbiazin.domain.PDV;
import br.com.tbiazin.domain.Produto;
import br.com.tbiazin.domain.ProdutoVenda;
import br.com.tbiazin.domain.Venda;
import br.com.tbiazin.service.interfaces.ICupomFiscalService;
import br.com.tbiazin.util.TipoDePagamentoEnum;

@EnableJpaRepositories
@SpringBootApplication
public class NewMarketApplication implements CommandLineRunner {

	@Autowired
	private ICupomFiscalService cupomFiscalService;

	public static void main(String[] args) {
		SpringApplication.run(NewMarketApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Venda> vendasCliente = new ArrayList<>();
		List<Venda> vendasPDV = new ArrayList<>();
		List<ProdutoVenda> vendasProduto1 = new ArrayList<>();
		List<ProdutoVenda> vendasProduto2 = new ArrayList<>();

		Cliente cliente = Cliente.builder().id(1L).nome("João Silva").cpf("123456789").email("joao.silva@example.com")
				.telefone("123456789").rua("Rua A").numero(100L).bairro("Bairro B").cidade("Cidade C").uf("UF")
				.vendas(vendasCliente).build();

		PDV pdv = PDV.builder().id(1L).nome("Caixa 30").build();

		Produto produto1 = Produto.builder().nome("Produto 414").descricao("Descrição 1")
				.preco(BigDecimal.valueOf(20.00)).tipo("Tipo 1").ean("8234567890127").dataCadastro(LocalDate.now())
				.estoque(500).vendas(vendasProduto1).build();

		Produto produto2 = Produto.builder().nome("Produto 158").descricao("Descrição 2")
				.preco(BigDecimal.valueOf(30.00)).tipo("Tipo 2").ean("8234567890127").dataCadastro(LocalDate.now())
				.estoque(500).vendas(vendasProduto2).build();

		ProdutoVenda produtoVenda1 = ProdutoVenda.builder().produto(produto1).venda(null).quantidade(2)
				.precoUnitario(produto1.getPreco().doubleValue()).build();

		ProdutoVenda produtoVenda2 = ProdutoVenda.builder().produto(produto2).venda(null).quantidade(1)
				.precoUnitario(produto2.getPreco().doubleValue()).build();

		List<ProdutoVenda> produtosVenda = new ArrayList<>();
		produtosVenda.add(produtoVenda1);
		produtosVenda.add(produtoVenda2);

		double valorTotal = produtosVenda.stream()
				.mapToDouble(p -> p.getProduto().getPreco().doubleValue() * p.getQuantidade()).sum();

		Venda venda = new Venda(1L, LocalDate.now(), cliente, pdv, produtosVenda, TipoDePagamentoEnum.DINHEIRO,
				valorTotal);

		produtoVenda1.setVenda(venda);
		produtoVenda2.setVenda(venda);

		vendasCliente.add(venda);
		vendasPDV.add(venda);
		vendasProduto1.add(produtoVenda1);
		vendasProduto2.add(produtoVenda2);

	    cupomFiscalService.gerarCupomFiscal(venda);

	}
}
