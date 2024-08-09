package br.com.tbiazin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.tbiazin.domain.Cliente;
import br.com.tbiazin.domain.ProdutoVenda;
import br.com.tbiazin.domain.Venda;
import br.com.tbiazin.repository.IVendaRepository;
import br.com.tbiazin.service.interfaces.IClienteService;
import br.com.tbiazin.service.interfaces.IVendaService;

@Service
public class VendaServices implements IVendaService {


	private final IVendaRepository vendaRepository;
	private final IClienteService clienteService;

	public VendaServices(IVendaRepository vendaRepository, IClienteService clienteRepository) {
		this.vendaRepository = vendaRepository;
		this.clienteService = clienteRepository;
	}

	@Override
	public Venda createVenda(Venda venda) {
		if (venda.getValorTotal() <= 0) {
			throw new IllegalArgumentException("O valor total da venda deve ser maior que zero.");
		}


		Cliente cliente = clienteService.getClienteById(venda.getCliente());
		if (cliente == null) {
			throw new IllegalArgumentException("Cliente não encontrado");
		}
		venda.setCliente(cliente);

		for (ProdutoVenda produtoVenda : venda.getProdutosVenda()) {
			produtoVenda.setVenda(venda);
		}

		return vendaRepository.save(venda);
	}

	@Override
	public void deleteVenda(Long id) {
		vendaRepository.deleteById(id);
	}

	@Override
	public List<Venda> getAllVendas() {
		System.out.println("Fetching all vendas");
		List<Venda> vendas = vendaRepository.findAll();
		System.out.println("Number of vendas found: " + vendas.size());
		return vendas;
	}

	@Override
	public Venda getVendaById(Long id) {
		Optional<Venda> venda = vendaRepository.findById(id);
		return venda.orElse(null);
	}

	@Override
	public Venda updateVenda(Venda venda) {
		if (vendaRepository.existsById(venda.getId())) {
			return vendaRepository.save(venda);
		}
		return null;
	}
}
