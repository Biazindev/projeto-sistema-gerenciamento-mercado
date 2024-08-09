package br.com.tbiazin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbiazin.domain.PDV;
import br.com.tbiazin.service.interfaces.IPDVService;

@RestController
@RequestMapping("/PDV")
@CrossOrigin(origins = "http://localhost:3000")
public class PDVController {

    @Autowired
    private IPDVService pdvService;
    
    @GetMapping()
	public ResponseEntity<List<PDV>> getAllPdv(){
		List<PDV> allPdvs = pdvService.getAllPDVs();
		return ResponseEntity.ok(allPdvs);}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PDV> findPDVById(@PathVariable Long id) {
		PDV pdv = pdvService.getPDVById(id);
		return ResponseEntity.ok(pdv);
	}

    @PostMapping
    public PDV createPdv(@RequestBody PDV pdv) {
        return pdvService.createPDV(pdv);
    }

    @PutMapping("/{id}")
    public PDV updatePdv(@PathVariable Long id, @RequestBody PDV pdv) {
        pdv.setId(id);
        return pdvService.updatePDV(pdv);
    }

    @DeleteMapping("/{id}")
    public void deletePdv(@PathVariable Long id) {
        pdvService.deletePDV(id);
    }
}


