package com.schons.vendas.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.schons.vendas.dto.PedidoDTO;
import com.schons.vendas.exception.ResourceNotFoundException;
import com.schons.vendas.model.Pedido;
import com.schons.vendas.repository.PedidoDAO;
import com.schons.vendas.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {
    
    private PedidoDAO pedidoDAO;
    private ModelMapper modelMapper;

    public PedidoServiceImpl(PedidoDAO pedidoDAO, ModelMapper modelMapper){
        this.pedidoDAO = pedidoDAO;
        this.modelMapper = modelMapper;
    }

    public PedidoDTO salvar(PedidoDTO pedidoDTO){
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        pedido.setClienteId(pedidoDTO.getClienteId());
        pedido.setData(pedidoDTO.getData());
        pedido.setProdutos(pedidoDTO.getProdutos());
        pedido.setValorTotal(pedidoDTO.getValorTotal());
        pedidoDAO.salvar(pedido);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> listarTodos(){
        return pedidoDAO.listarTodos().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public boolean deleteById(int id){
        boolean deletado = pedidoDAO.deleteById(id);
        if(deletado == true){
            return true;
        }
        return false;
    }

    public Optional<PedidoDTO> getById(int id){
       return pedidoDAO.getById(id).map(pedido -> modelMapper.map(pedido, PedidoDTO.class));
    }

    public boolean atualizar(int id, PedidoDTO pedidoDTO){
        if(pedidoDAO.getById(id).isEmpty()){
            throw new ResourceNotFoundException("Pedido com id: " + id + " nao encontrado");
        }
        Pedido pedido = modelMapper.map(pedidoDTO, Pedido.class);
        pedido.setId(id);
        return pedidoDAO.atualizar(id, pedido);
    }

    public Optional<PedidoDTO> getByClienteId(int clienteId){
        return pedidoDAO.getByClienteId(clienteId).map(pedido -> modelMapper.map(pedido, PedidoDTO.class));
    }
    

}
