package br.com.tbiazin.repository;

import br.com.tbiazin.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutoRepository extends JpaRepository<Produto, Long> {

}
