package br.com.tbiazin.service.interfaces;

import br.com.tbiazin.domain.ProdutoVenda;

import java.util.List;

public interface IProdutoVendaService {
    ProdutoVenda createProdutoVenda(ProdutoVenda produtoVenda);
    ProdutoVenda updateProdutoVenda(ProdutoVenda produtoVenda);
    void deleteProdutoVenda(Long id);
    ProdutoVenda getProdutoVendaById(Long id);
    List<ProdutoVenda> getAllProdutoVendas();
    List<ProdutoVenda> findByVendaId(Long vendaId);

}
