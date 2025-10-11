package com.schons.vendas.model;

public class ItemPedido {
    
    private int id;
    private int produtoId;
    private int pedidoId;
    private int quantidade;
    private double valorUnitario;

    public ItemPedido(int produtoId, int pedidoId, int quantidade, double valorUnitario){
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.pedidoId = pedidoId;
    }

    public ItemPedido(){};

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(int produto) {
        this.produtoId = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getValor_unitario() {
        return valorUnitario;
    }
    public void setValor_unitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    

}
