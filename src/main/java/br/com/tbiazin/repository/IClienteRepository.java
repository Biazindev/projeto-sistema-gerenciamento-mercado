package br.com.tbiazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tbiazin.domain.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

}
