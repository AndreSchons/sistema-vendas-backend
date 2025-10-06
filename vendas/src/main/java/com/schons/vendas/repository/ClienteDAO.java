package com.schons.vendas.repository;

import java.util.List;
import java.util.Optional;

import com.schons.vendas.model.Cliente;

public interface ClienteDAO {
    
    Cliente salvar(Cliente cliente);
    List<Cliente> listarTodos();
    boolean deleteById(int id);
    Optional<Cliente> getById(int id);
    Cliente atualizar(int id, Cliente cliente);
}
