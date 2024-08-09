package br.com.tbiazin.repository;

import br.com.tbiazin.domain.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
}
