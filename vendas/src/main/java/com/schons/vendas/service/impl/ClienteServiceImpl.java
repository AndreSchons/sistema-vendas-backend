package com.schons.vendas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.schons.vendas.dto.ClienteDTO;
import com.schons.vendas.exception.ProdutoNotFoundException;
import com.schons.vendas.model.Cliente;
import com.schons.vendas.repository.ClienteDAO;
import com.schons.vendas.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

    private final ClienteDAO clienteDao;
    private final ModelMapper modelMapper;

    public ClienteServiceImpl(ClienteDAO clienteDao, ModelMapper modelMapper){
        this.clienteDao = clienteDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        clienteDao.salvar(cliente);
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> listarTodos(){
        return clienteDao.listarTodos().stream()
            .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(int id){
        boolean removido = clienteDao.deleteById(id);
        if(!removido){
            return false;
        }
        return true;
    }

    @Override
    public ClienteDTO getById(int id){
        Cliente cliente = clienteDao.getById(id).orElseThrow(() -> new ProdutoNotFoundException("Cliente com o id " + id + " nao encontrado"));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO atualizar(int id, ClienteDTO clienteDTO){
        Cliente clienteExistente = clienteDao.getById(id).orElseThrow(() -> new ProdutoNotFoundException("Cliente com o id " + id + " nao encontrado"));
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setEmail(clienteDTO.getEmail());
        
        Cliente produtoAtualizado = clienteDao.atualizar(id, clienteExistente);
        return modelMapper.map(produtoAtualizado, ClienteDTO.class);
    }
}