package br.com.tbiazin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tbiazin.domain.ProdutoVenda;

public interface IProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {
    List<ProdutoVenda> findByVendaId(Long vendaId);

    }
