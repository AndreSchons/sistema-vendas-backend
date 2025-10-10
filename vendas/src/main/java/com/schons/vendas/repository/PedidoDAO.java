package com.schons.vendas.repository;

import java.util.List;
import java.util.Optional;
import com.schons.vendas.model.Pedido;

public interface PedidoDAO {
    
    Pedido salvar(Pedido pedido);
    List<Pedido> listarTodos();
    boolean deleteById(int id);
    Optional<Pedido> getById(int id);
}
