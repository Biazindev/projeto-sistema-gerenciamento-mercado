package br.com.tbiazin.dao;

import java.util.List;

import br.com.tbiazin.domain.Fornecedor;

public interface IFornecedorDao {

	public List<Fornecedor> getAll(Fornecedor fornecedor);
	
	public void deleteById (Fornecedor fornecedor);
	
	public Fornecedor cadastrar(Fornecedor fornecedor);

	Fornecedor update(long id, Fornecedor novosDados);
}
