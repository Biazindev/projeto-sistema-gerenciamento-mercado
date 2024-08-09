package br.com.tbiazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tbiazin.domain.Venda;

@Repository
public interface IVendaRepository extends JpaRepository<Venda, Long> {
}
