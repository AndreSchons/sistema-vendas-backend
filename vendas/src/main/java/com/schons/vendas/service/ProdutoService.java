package com.schons.vendas.service;

import java.util.List;

import com.schons.vendas.dto.ProdutoDTO;

public interface ProdutoService {

    ProdutoDTO salvar(ProdutoDTO produtoDto);
    List<ProdutoDTO> listarTodos();
    void deleteById(int id);
    ProdutoDTO getById(int id);
    ProdutoDTO atualizar(int id, ProdutoDTO produtoDto);
}
