package com.schons.vendas.service;

import java.util.List;
import com.schons.vendas.dto.ClienteDTO;

public interface ClienteService {

    ClienteDTO salvar(ClienteDTO clienteDTO);
    List<ClienteDTO> listarTodos();
    boolean deleteById(int id);
    ClienteDTO getById(int id);
    ClienteDTO atualizar(int id, ClienteDTO clienteDTO);
}