package com.schons.vendas.repository.impl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schons.vendas.repository.ProdutoDAO;
import com.schons.vendas.exception.ProdutoNotFoundException;
import com.schons.vendas.model.Produto;

@Repository
public class ProdutoDAOImpl implements ProdutoDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Produto salvar(Produto produto){
        String sql = "INSERT INTO produtos(nome,descricao,preco) VALUES(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexao -> {
            PreparedStatement stmt = conexao.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            return stmt;
        }, keyHolder);
        int idGerado = keyHolder.getKey().intValue();
        produto.setId(idGerado);
        return produto;
    }

    @Override
    public List<Produto> listarTodos(){
        String sql = "SELECT * FROM produtos";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Produto.class));
    }

    @Override
    public boolean deleteById(int id){
        String sql = "DELETE FROM produtos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if(rowsAffected != 0){
            return true;
        }
        return false;
    }

    public Optional<Produto> getById(int id){
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try {
            Produto produto = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Produto.class),id);
            return Optional.of(produto);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Produto atualizar(int id, Produto produto){
        String sql = """
                    UPDATE produtos
                        SET nome = ?,
                            descricao = ?,
                            preco = ?
                        WHERE id = ?;
                """;
        int rowsAffected = jdbcTemplate.update(sql, produto.getNome(), produto.getDescricao(),produto.getPreco(), id);
        if(rowsAffected == 0){
            throw new ProdutoNotFoundException("Produto nao encontrado");
        }
        produto.setId(id);
        return produto;
    }
}
