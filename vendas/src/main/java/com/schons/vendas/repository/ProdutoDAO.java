package com.schons.vendas.repository;

import java.util.List;
import java.util.Optional;

import com.schons.vendas.model.Produto;

public interface ProdutoDAO {

    Produto salvar(Produto produto);
    List<Produto> listarTodos();
    boolean deleteById(int id);
    Optional<Produto> getById(int id);
    Produto atualizar(int id, Produto produto);
}
