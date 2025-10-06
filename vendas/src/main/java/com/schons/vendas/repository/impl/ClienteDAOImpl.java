package com.schons.vendas.repository.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.schons.vendas.model.Cliente;
import com.schons.vendas.repository.ClienteDAO;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Cliente salvar(Cliente cliente){
        String sql = "INSERT INTO clientes (nome,email) VALUES (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexao -> {
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            return stmt;
        }, keyHolder);  
        int idGerado = keyHolder.getKey().intValue();
        cliente.setId(idGerado);
        return cliente;      
    }

    @Override
    public List<Cliente> listarTodos(){
        String sql = "SELECT * FROM clientes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }

    @Override
    public boolean deleteById(int id){
        String sql = "DELETE FROM clientes WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if(rowsAffected != 0){
            return true;
        }
        return false;
    }

    public Optional<Cliente> getById(int id){
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try {
            Cliente cliente = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class),id);
            return Optional.of(cliente);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Cliente atualizar(int id, Cliente cliente){
        String sql = """
                    UPDATE clientes
                        SET nome = ?,
                            email = ?,
                        WHERE id = ?;
                """;
        int rowsAffected = jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), id);
        if(rowsAffected == 0){
            throw new RuntimeException("Produto nao encontrado");
        }
        cliente.setId(id);
        return cliente;
    }
}