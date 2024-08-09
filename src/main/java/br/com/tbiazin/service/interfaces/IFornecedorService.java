package br.com.tbiazin.service.interfaces;

import java.util.List;

import br.com.tbiazin.domain.Fornecedor;

public interface IFornecedorService {

	Fornecedor findFornecedorById(long id);

	List<Fornecedor> getAllFornecedores();

	void deleteFornecedorByID(long id);

	Fornecedor updateByID(long id, Fornecedor fornecedor);

	Fornecedor createFornecedor(Fornecedor fornecedor);

}
