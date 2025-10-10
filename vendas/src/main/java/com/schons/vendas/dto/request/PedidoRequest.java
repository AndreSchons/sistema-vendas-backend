package com.schons.vendas.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schons.vendas.model.ItemPedido;

public class PedidoRequest {
    
    
    private int clienteId;
    private List<ItemPedido> produtos;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private double valorTotal;

    public PedidoRequest(int clienteId, List<ItemPedido> produtos, LocalDate data, double valorTotal){
        this.clienteId = clienteId;
        this.produtos = produtos;
        this.data = data;
        this.valorTotal = produtos.stream().mapToDouble(item -> item.getValor_unitario() * item.getQuantidade()).sum();
    }

    public PedidoRequest(){}

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedido> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemPedido> produtos) {
        this.produtos = produtos;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    


    

    
}
