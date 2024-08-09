package tests.ProductRepositoryTest;

import br.com.tbiazin.domain.Produto;
import br.com.tbiazin.domain.ProdutoVenda;
import br.com.tbiazin.repository.IProdutoRepository;
import br.com.tbiazin.service.ProdutoServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProductTest {

    @Configuration
    @ComponentScan(basePackages = "br.com.tbiazin")
    static class TestConfig {
        @Bean
        public ProdutoServices produtoServices(IProdutoRepository produtoRepository) {
            return new ProdutoServices(produtoRepository);
        }
    }

    @Autowired
    ProdutoServices service;

    @Autowired
    IProdutoRepository repository;

    List<ProdutoVenda> vendasProduto1 = new ArrayList<>();

    Produto productMock = Produto.builder()
            .nome("Produto 414")
            .descricao("Descrição 1")
            .preco(BigDecimal.valueOf(20.00))
            .tipo("Tipo 1")
            .ean("8234567890127")
            .dataCadastro(LocalDate.now())
            .estoque(500)
            .vendas(vendasProduto1)
            .build();

    @Test
    @DisplayName("Inserção de produtos")
    public void insertProduct() {
        Produto insertProduct = service.createProduto(productMock);
        Optional<Produto> result = repository.findById(insertProduct.getId());
        assertThat(result.isPresent()).isTrue();
    }
}
