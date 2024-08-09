package br.com.tbiazin.service;

import br.com.tbiazin.domain.ProdutoVenda;
import br.com.tbiazin.repository.IProdutoVendaRepository;
import br.com.tbiazin.service.interfaces.IProdutoVendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoVendaServices implements IProdutoVendaService {
    @Autowired
    private final IProdutoVendaRepository produtoVendaRepository;


    public ProdutoVendaServices(IProdutoVendaRepository produtoVendaRepository) {
        this.produtoVendaRepository = produtoVendaRepository;
    }

    @Override
    public ProdutoVenda createProdutoVenda(ProdutoVenda produtoVenda) {
        return produtoVendaRepository.save(produtoVenda);
    }

    @Override
    public ProdutoVenda updateProdutoVenda(ProdutoVenda produtoVenda) {
        return produtoVendaRepository.save(produtoVenda);
    }

    @Override
    public void deleteProdutoVenda(Long id) {
        produtoVendaRepository.deleteById(id);
    }

    @Override
    public ProdutoVenda getProdutoVendaById(Long id) {
        Optional<ProdutoVenda> produtoVenda = produtoVendaRepository.findById(id);
        return produtoVenda.orElse(null);
    }

    @Override
    public List<ProdutoVenda> getAllProdutoVendas() {
        return produtoVendaRepository.findAll();
    }
 

	@Override
	public List<ProdutoVenda> findByVendaId(Long vendaId) {
		return produtoVendaRepository.findByVendaId(vendaId);
	}
}
