package br.com.tbiazin.service;

import br.com.tbiazin.domain.Produto;
import br.com.tbiazin.repository.IProdutoRepository;
import br.com.tbiazin.service.interfaces.IProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServices implements IProdutoService {

    @Autowired
    private final IProdutoRepository produtoRepository;

    public ProdutoServices(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto createProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public Produto updateProduto(Long id, Produto produto) {
        if (produtoRepository.existsById(produto.getId())) {
            return produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Produto getProdutoById(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }
}
