package br.com.tbiazin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tbiazin.domain.Fornecedor;

@Repository
public interface IFornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
