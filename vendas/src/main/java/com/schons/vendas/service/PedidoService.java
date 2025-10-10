package com.schons.vendas.service;

import java.util.List;
import java.util.Optional;
import com.schons.vendas.dto.PedidoDTO;

public interface PedidoService {
    
    PedidoDTO salvar(PedidoDTO pedidoDTO);
    List<PedidoDTO> listarTodos();
    boolean deleteById(int id);
    Optional<PedidoDTO> getById(int id);
}
