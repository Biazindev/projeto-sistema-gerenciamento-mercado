package br.com.tbiazin.service.interfaces;

import br.com.tbiazin.domain.Cliente;

import java.util.List;


public interface IClienteService {
	    Cliente createCliente(Cliente cliente);
	    Cliente updateCliente(Long id, Cliente cliente);
	    boolean deleteCliente(Long id);
	    List<Cliente> getAllClientes();
	    Cliente getClienteById(Long id);
		Cliente getClienteById(Cliente cliente);
}
