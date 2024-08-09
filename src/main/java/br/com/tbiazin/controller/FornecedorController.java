package br.com.tbiazin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbiazin.domain.Fornecedor;
import br.com.tbiazin.service.interfaces.IFornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private IFornecedorService fornecedorService;

	@GetMapping
	public ResponseEntity<List<Fornecedor>> getAllFornecedores() {
		List<Fornecedor> fornecedores = fornecedorService.getAllFornecedores();
		return ResponseEntity.ok(fornecedores);
	}
	@PostMapping("/criar")
	public ResponseEntity<Fornecedor>  createFornecedor(Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.createFornecedor(fornecedor);
		return ResponseEntity.ok(novoFornecedor);
		
		
	}
}

//preciso terminar de add os métodos e também popular a tabela nova