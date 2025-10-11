package com.schons.vendas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.schons.vendas.service.impl.ProdutoServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schons.vendas.dto.PedidoDTO;
import com.schons.vendas.dto.request.PedidoRequest;
import com.schons.vendas.dto.response.PedidoResponse;
import com.schons.vendas.service.impl.PedidoServiceImpl;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    
    private ModelMapper modelMapper;
    private PedidoServiceImpl pedidoServiceImpl;

    public PedidoController(ModelMapper modelMapper, PedidoServiceImpl pedidoServiceImpl, ProdutoServiceImpl produtoServiceImpl){
        this.modelMapper = modelMapper;
        this.pedidoServiceImpl = pedidoServiceImpl;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> salvar(@RequestBody PedidoRequest pedidoRequest){
        PedidoDTO pedido = modelMapper.map(pedidoRequest, PedidoDTO.class);
        PedidoDTO pedidoSalvo = pedidoServiceImpl.salvar(pedido);
        PedidoResponse pedidoResponse = modelMapper.map(pedidoSalvo, PedidoResponse.class);
        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarTodos(){
        List<PedidoResponse> pedidos = pedidoServiceImpl.listarTodos().stream()
        .map(pedido -> modelMapper.map(pedido, PedidoResponse.class))
        .collect(Collectors.toList());
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable int id){
        if(pedidoServiceImpl.deleteById(id) == true){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PedidoResponse>> getById(@PathVariable int id){
        Optional<PedidoDTO> pedidoOpt = pedidoServiceImpl.getById(id);
        if(pedidoOpt.isPresent()){
            return ResponseEntity.ok(pedidoOpt.map(pedido -> modelMapper.map(pedido, PedidoResponse.class)));
        }
        return ResponseEntity.of(Optional.empty());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<PedidoResponse>> getByClienteId(@PathVariable int id){
        List<PedidoDTO> pedidos = pedidoServiceImpl.getByClienteId(id);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PedidoResponse> response = pedidos.stream()
        .map(pedido -> modelMapper.map(pedido, PedidoResponse.class))
        .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> atualizar(@PathVariable int id, @RequestBody PedidoRequest pedidoRequest){
        PedidoDTO pedidoDTO = modelMapper.map(pedidoRequest, PedidoDTO.class);
        boolean atualizado = pedidoServiceImpl.atualizar(id, pedidoDTO);
        if(atualizado){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}