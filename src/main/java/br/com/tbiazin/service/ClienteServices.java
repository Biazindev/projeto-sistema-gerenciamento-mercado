package br.com.tbiazin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tbiazin.domain.Cliente;
import br.com.tbiazin.repository.IClienteRepository;
import br.com.tbiazin.service.interfaces.IClienteService;

@Service
public class ClienteServices implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            cliente.setId(id);
            return clienteRepository.save(cliente);
        }
        return null;
    }

    public boolean deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

	@Override
	public Cliente getClienteById(Cliente cliente) {
        return clienteRepository.findById(cliente.getId()).orElse(null);
	}
}
