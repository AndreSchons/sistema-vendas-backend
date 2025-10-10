package com.schons.vendas.dto;

import java.sql.Date;
import java.util.List;

import com.schons.vendas.model.ItemPedido;

public class PedidoDTO {
    
    private int id;
    private int clienteId;
    private List<ItemPedido> produtos;
    private Date data;
    private double valorTotal;

    public PedidoDTO(int clienteId, List<ItemPedido> produtos, Date data, double valorTotal){
        this.clienteId = clienteId;
        this.produtos = produtos;
        this.data = data;
        this.valorTotal = produtos.stream().mapToDouble(item -> item.getValor_unitario() * item.getQuantidade()).sum();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public Date getDate() {
        return data;
    }
    public void setDate(Date date) {
        this.data = date;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
