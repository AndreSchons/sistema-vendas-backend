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

import com.schons.vendas.model.ItemPedido;
import com.schons.vendas.model.Pedido;
import com.schons.vendas.repository.PedidoDAO;

@Repository
public class PedidoDAOImpl implements PedidoDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Pedido salvar(Pedido pedido){
        String sql = "INSERT INTO pedidos (cliente_id, data, valor_total) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexao -> {
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, pedido.getClienteId());
            stmt.setDate(2, java.sql.Date.valueOf(pedido.getData()));
            stmt.setDouble(3, pedido.getValorTotal());
            return stmt;
        }, keyHolder);
        int idGerado = keyHolder.getKey().intValue();

        String sqlItemPedido = "INSERT INTO itempedido (produto_id, pedido_id, quantidade, valor_unitario) VALUES (?,?,?,?)";
        for(ItemPedido item : pedido.getProdutos()){
            jdbcTemplate.update(conexao ->{
                PreparedStatement stmt = conexao.prepareStatement(sqlItemPedido);
                stmt.setInt(1, item.getProdutoId());
                stmt.setInt(2, idGerado);
                stmt.setInt(3, item.getQuantidade());
                stmt.setDouble(4, item.getValorUnitario());
                return stmt;
            });
        }
        pedido.setId(idGerado);
        return pedido;
    }

    public List<Pedido> listarTodos(){
        String sql = "SELECT * FROM pedidos";
        List<Pedido> pedidos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class));

        String sqlItens = "SELECT * FROM itempedido WHERE pedido_id = ?";
        for(Pedido pedido : pedidos){
            List<ItemPedido> itens = jdbcTemplate.query(sqlItens, new BeanPropertyRowMapper<>(ItemPedido.class),pedido.getId());
            pedido.setProdutos(itens);
        }
        return pedidos;
    }

    public boolean deleteById(int id){
        String sql = "DELETE FROM pedidos WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if(rowsAffected != 0){
            return true;
        }
        return false;
    } 

    public Optional<Pedido> getById(int id){
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try {
            Pedido pedido = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pedido.class),id);
            String sqlItem = "SELECT * FROM itempedido WHERE pedido_id = ?";
            List<ItemPedido> itens = jdbcTemplate.query(sqlItem, new BeanPropertyRowMapper<>(ItemPedido.class),id);
            pedido.setProdutos(itens);
            return Optional.of(pedido);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean atualizar(int id, Pedido pedido){
        String sql = "UPDATE pedidos SET cliente_id = ?, data = ?, valor_total = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, pedido.getClienteId(),
                                pedido.getData(),
                                pedido.getValorTotal(),
                                id);
        if(rowsAffected == 0){
            return false;
        }

        String sqlDelete = "DELETE FROM itempedido WHERE pedido_id = ?";
        jdbcTemplate.update(sqlDelete, id);

        String sqlInser = "INSERT INTO itempedido (produto_id, pedido_id, quantidade, valor_unitario) VALUES (?,?,?,?)";
        for(ItemPedido item : pedido.getProdutos()){
            jdbcTemplate.update(sqlInser, item.getProdutoId(), id, item.getQuantidade(), item.getValorUnitario());
        }
        return true;
    }

    public List<Pedido> getByClienteId(int clienteId){
        String sql = "SELECT * FROM pedidos WHERE cliente_id = ?";
        List<Pedido> pedidos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pedido.class), clienteId);

        String sqlItem = "SELECT * FROM itempedido WHERE pedido_id = ?";
        for(Pedido pedido : pedidos){
            List<ItemPedido> itens = jdbcTemplate.query(sqlItem, new BeanPropertyRowMapper<>(ItemPedido.class),pedido.getId());
            pedido.setProdutos(itens);
        }
        return pedidos;
    }
}