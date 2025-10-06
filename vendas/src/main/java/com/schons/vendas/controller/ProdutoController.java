package com.schons.vendas.controller;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.schons.vendas.dto.ProdutoDTO;
import com.schons.vendas.dto.request.ProdutoRequest;
import com.schons.vendas.dto.response.ProdutoResponse;
import com.schons.vendas.service.impl.ProdutoServiceImpl;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    @Autowired
    private final ModelMapper modelMapper;
    
    @Autowired
    private ProdutoServiceImpl produtoServiceImpl;

    ProdutoController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody ProdutoRequest produtoRequest){
        ProdutoDTO produto = modelMapper.map(produtoRequest, ProdutoDTO.class);
        ProdutoDTO produtoSalvo = produtoServiceImpl.salvar(produto);
        ProdutoResponse produtoResponse = modelMapper.map(produtoSalvo, ProdutoResponse.class);
        return ResponseEntity.ok(produtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarTodos(){
        List<ProdutoDTO> produtos = produtoServiceImpl.listarTodos();

        List<ProdutoResponse> responses = produtos.stream()
            .map(produto -> modelMapper.map(produto, ProdutoResponse.class))
            .toList();
        
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        boolean removido = produtoServiceImpl.deleteById(id);
        if(!removido){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getById(@PathVariable int id){
        ProdutoDTO produto = produtoServiceImpl.getById(id);
        if(produto == null){
            return ResponseEntity.notFound().build();
        }
        ProdutoResponse response = modelMapper.map(produto, ProdutoResponse.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable int id, @RequestBody ProdutoRequest produtoRequest){
        ProdutoDTO verificarProduto = produtoServiceImpl.getById(id);
        if(verificarProduto == null){
            return ResponseEntity.notFound().build();
        }
        ProdutoDTO produtoDTO = modelMapper.map(produtoRequest, ProdutoDTO.class);
        produtoDTO.setId(id);
        produtoDTO = produtoServiceImpl.atualizar(id, produtoDTO);
        ProdutoResponse produtoResponse = modelMapper.map(produtoDTO, ProdutoResponse.class);
        return ResponseEntity.ok(produtoResponse); 
    }


}
