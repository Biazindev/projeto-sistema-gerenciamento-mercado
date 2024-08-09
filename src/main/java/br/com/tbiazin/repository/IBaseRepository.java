package br.com.tbiazin.repository;

import java.util.List;

import br.com.tbiazin.domain.Produto;

public interface IBaseRepository<T, ID> {
    T save(Produto produto);
    T update(T entity);
    void delete(ID id);
    T findById(ID id);
    List<T> findAll();
}



