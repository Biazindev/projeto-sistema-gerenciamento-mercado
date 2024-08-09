package br.com.tbiazin.dao;

import java.util.List;

import br.com.tbiazin.domain.Cliente;

public interface IClienteDao implements {
	
	public Cliente cadastrar(Cliente cliente);

	public void excluir(Cliente cliente);

	public List<Cliente> buscarTodos();

	public Cliente buscarPorId(long l);

}
