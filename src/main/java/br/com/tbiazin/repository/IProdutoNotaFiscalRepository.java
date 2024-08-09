package br.com.tbiazin.repository;

import br.com.tbiazin.domain.ProdutoNotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoNotaFiscalRepository extends JpaRepository<ProdutoNotaFiscal, Long> {
}
