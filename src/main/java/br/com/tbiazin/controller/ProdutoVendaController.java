package br.com.tbiazin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbiazin.domain.ProdutoVenda;
import br.com.tbiazin.service.interfaces.IProdutoVendaService;

@RestController
@RequestMapping("/produtosVenda")
public class ProdutoVendaController {

    @Autowired
    private IProdutoVendaService IprodutoService;
    

    @PostMapping
    public ProdutoVenda criarProduto(@RequestBody ProdutoVenda produto) {
        return IprodutoService.createProdutoVenda(produto);
    }
    
    @GetMapping
    public ResponseEntity<List<ProdutoVenda>> getAllProdutos() {
        List<ProdutoVenda> produtos = IprodutoService.getAllProdutoVendas();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public Optional<ProdutoVenda> buscarProduto(@PathVariable Long id) {
        return Optional.of(IprodutoService.getProdutoVendaById(id));
    }
    @GetMapping("/venda/{vendaId}")
    public ResponseEntity<List<ProdutoVenda>> buscarProdutosPorVenda(@PathVariable Long vendaId) {
        try {
            List<ProdutoVenda> produtosVenda = IprodutoService.findByVendaId(vendaId);
            return ResponseEntity.ok(produtosVenda);
        } catch (Exception e) {
            // Log the exception and return a 500 error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
