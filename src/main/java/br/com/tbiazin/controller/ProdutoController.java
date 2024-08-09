package br.com.tbiazin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbiazin.domain.Produto;
import br.com.tbiazin.service.interfaces.IProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private IProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<Produto>> getAllProdutos() {
		List<Produto> produtos = produtoService.getAllProdutos();
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarProduto(@PathVariable Long id) {
		Produto produto = produtoService.getProdutoById(id);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
		Produto novoProduto = produtoService.createProduto(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
		produtoService.deleteProduto(id);
		return ResponseEntity.noContent().build();
	}
	
	 @PutMapping("/{id}")
	    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
	        Produto produtoAtualizado = produtoService.updateProduto(id, produto);
	        return produtoAtualizado != null ? ResponseEntity.ok(produtoAtualizado) : ResponseEntity.notFound().build();
	    }
}
