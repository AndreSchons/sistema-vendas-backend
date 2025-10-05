package com.schons.vendas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.schons.vendas.dto.ProdutoDTO;
import com.schons.vendas.exception.ProdutoNotFoundException;
import com.schons.vendas.model.Produto;
import com.schons.vendas.repository.ProdutoDAO;
import com.schons.vendas.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    
    
    private final ProdutoDAO produtoDAO;
    private final ModelMapper modelMapper;

    public ProdutoServiceImpl(ProdutoDAO produtoDAO, ModelMapper modelMapper){
        this.produtoDAO = produtoDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProdutoDTO salvar(ProdutoDTO produtoDTO) {
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        produtoDAO.salvar(produto);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Override
    public List<ProdutoDTO> listarTodos(){
        return produtoDAO.listarTodos().stream()
            .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id){
        produtoDAO.deleteById(id);
    }

    @Override
    public ProdutoDTO getById(int id){
        Produto produto = produtoDAO.getById(id).orElseThrow(() -> new ProdutoNotFoundException("Produto com o id " + id + " nao encontrado"));
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Override
    public ProdutoDTO atualizar(int id, ProdutoDTO produtoDTO){
        Produto produtoExistente = produtoDAO.getById(id).orElseThrow(() -> new ProdutoNotFoundException("Produto com o id " + id + " nao encontrado"));
        produtoExistente.setNome(produtoDTO.getNome());
        produtoExistente.setDescricao(produtoDTO.getDescricao());
        produtoExistente.setPreco(produtoDTO.getPreco());
        
        Produto produtoAtualizado = produtoDAO.atualizar(id, produtoExistente);
        return modelMapper.map(produtoAtualizado, ProdutoDTO.class);
    }
}
