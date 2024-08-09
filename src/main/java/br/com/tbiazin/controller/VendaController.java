package br.com.tbiazin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbiazin.domain.Venda;
import br.com.tbiazin.service.interfaces.IVendaService;

@RestController
@RequestMapping("/vendas")

public class VendaController {
	 @Autowired
    private  IVendaService vendaService;

   
    public VendaController(IVendaService vendaService) {
        this.vendaService = vendaService;
  
    }

    @PostMapping
    public ResponseEntity<Venda> createVenda(@RequestBody Venda venda) {
        Venda novaVenda = vendaService.createVenda(venda);
        return ResponseEntity.ok(novaVenda);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> getAllVendas() {
        List<Venda> vendas = vendaService.getAllVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendasByID(@PathVariable Long id) {
        Venda venda = vendaService.getVendaById(id);
        return ResponseEntity.ok(venda);
    }

}
