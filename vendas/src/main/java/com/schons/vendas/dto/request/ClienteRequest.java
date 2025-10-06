package com.schons.vendas.dto.request;

public class ClienteRequest {
    
    private String nome;
    private String email;

    public ClienteRequest(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public ClienteRequest(){};

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
