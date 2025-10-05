package com.schons.vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schons.vendas.dto.ProdutoDTO;
import com.schons.vendas.service.impl.ProdutoServiceImpl;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoServiceImpl produtoServiceImpl;

    @PostMapping
    public ProdutoDTO salvar(@RequestBody ProdutoDTO produtoDTO){
        return produtoServiceImpl.salvar(produtoDTO);
    }
}
