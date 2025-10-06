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

import com.schons.vendas.dto.ClienteDTO;
import com.schons.vendas.dto.request.ClienteRequest;
import com.schons.vendas.dto.response.ClienteResponse;
import com.schons.vendas.service.impl.ClienteServiceImpl;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private final ModelMapper modelMapper;
    
    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    ClienteController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> salvar(@RequestBody ClienteRequest clienteRequest){
        ClienteDTO cliente = modelMapper.map(clienteRequest, ClienteDTO.class);
        ClienteDTO clienteSalvo = clienteServiceImpl.salvar(cliente);
        ClienteResponse clienteResponse = modelMapper.map(clienteSalvo, ClienteResponse.class);
        return ResponseEntity.ok(clienteResponse);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarTodos(){
        List<ClienteDTO> clientes = clienteServiceImpl.listarTodos();

        List<ClienteResponse> responses = clientes.stream()
            .map(cliente -> modelMapper.map(cliente, ClienteResponse.class))
            .toList();
        
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        boolean removido = clienteServiceImpl.deleteById(id);
        if(!removido){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable int id){
        ClienteDTO cliente = clienteServiceImpl.getById(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        ClienteResponse response = modelMapper.map(cliente, ClienteResponse.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable int id, @RequestBody ClienteRequest clienteRequest){
        ClienteDTO verificarCliente = clienteServiceImpl.getById(id);
        if(verificarCliente == null){
            return ResponseEntity.notFound().build();
        }
        ClienteDTO clienteDTO = modelMapper.map(clienteRequest, ClienteDTO.class);
        clienteDTO.setId(id);
        clienteDTO = clienteServiceImpl.atualizar(id, clienteDTO);
        ClienteResponse clienteResponse = modelMapper.map(clienteDTO, ClienteResponse.class);
        return ResponseEntity.ok(clienteResponse); 
    }
}
