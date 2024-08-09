package br.com.tbiazin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tbiazin.domain.Fornecedor;
import br.com.tbiazin.repository.IFornecedorRepository;
import br.com.tbiazin.service.interfaces.IFornecedorService;

@Service
public class FornecedorService implements IFornecedorService {

	@Autowired
	IFornecedorRepository iFornecedorRepository;

	public Fornecedor findFornecedorById(long id) {
		return this.iFornecedorRepository.findById(id).orElse(null);
	}

	public List<Fornecedor> getAllFornecedores() {
		return iFornecedorRepository.findAll();
	}

	public void deleteFornecedorByID(long id) {
		iFornecedorRepository.deleteById(id);
	}

	public Fornecedor updateByID(long id, Fornecedor fornecedor) {
		if (iFornecedorRepository.existsById(id)) {
			fornecedor.setId(id);
			return iFornecedorRepository.save(fornecedor);
		}
		return null;
	}

	public Fornecedor createFornecedor(Fornecedor fornecedor) {
		return iFornecedorRepository.save(fornecedor);
	}
}