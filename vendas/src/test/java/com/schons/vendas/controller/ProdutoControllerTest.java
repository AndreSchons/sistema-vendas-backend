package com.schons.vendas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schons.vendas.dto.ProdutoDTO;
import com.schons.vendas.dto.request.ProdutoRequest;
import com.schons.vendas.dto.response.ProdutoResponse;
import com.schons.vendas.model.Produto;
import com.schons.vendas.service.impl.ProdutoServiceImpl;

@WebMvcTest(ProdutoController.class)
@Import(ProdutoControllerTest.TestConfig.class) 
public class ProdutoControllerTest {
    
    
    static class TestConfig{
        @Bean
        public ModelMapper modelMapper(){
            return new ModelMapper();
        }
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoServiceImpl produtoServiceImpl;

    //Add a classe do controller dentro do Mock para ele usar ela como referencia.
    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    public void deve_adicionar_o_produto() throws Exception{
        //Arrange: Preparacao.
        ProdutoRequest produtoRequest = new ProdutoRequest("Ipad", 2000, "Design by Apple");
        ProdutoDTO produtoDTO = modelMapper.map(produtoRequest, ProdutoDTO.class);

        //Convertendo o Objeto em um JSON.
        String json = new ObjectMapper().writeValueAsString(produtoRequest);

        //Criando a requisição do tipo POST e passando nosso JSON.
        var requestBuilder = MockMvcRequestBuilders.post("/produtos")
        .content(json).contentType(MediaType.APPLICATION_JSON);

        //Spring mocka o service para nao dar erro como o service nao e da classe controller
        when(this.produtoServiceImpl.salvar(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        //Act:Acao
        this.mockMvc.perform(requestBuilder)
        //Assert:Confirmacao
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deve_retornar_uma_lista_de_produtos_e_status_200() throws Exception{
        //Arrange: Preparacao
        List<ProdutoDTO> produtos = new ArrayList<>();
        //Criano a requisicao do tipo get para retornar a lista de produtos
        var requestBuilder = MockMvcRequestBuilders.get("/produtos");
        //Mockando o service para nao soltar uma Exception
        when(this.produtoServiceImpl.listarTodos()).thenReturn(produtos);
        
        //Act + Assert: Acao + confirmacao
        //Envia nossa requisicao
        this.mockMvc.perform(requestBuilder)
        //Retorna o status da nossa requisicao
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deve_deletar_o_produto_e_retornar_um_no_content()throws Exception{
        //Arrange:Preparacao
        int id = 1;
        when(produtoServiceImpl.deleteById(id)).thenReturn(true);       
        //Act + Assert : Acao + Confirmacao
        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/delete/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deve_retornar_o_codigo_404_quando_passar_o_id_inexistente() throws Exception{
        //Arrange
        int id = 999;
        when(produtoServiceImpl.deleteById(id)).thenReturn(false);
        var requestBuilder = MockMvcRequestBuilders.delete("/produtos/delete/{id}",id);
        //Act + Assert
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deve_retornar_o_produto_e_o_codigo_200() throws Exception{
        //Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1);
        produtoDTO.setNome("Panela");

        int id = 1;
        var requestBuilder = MockMvcRequestBuilders.get("/produtos/{id}", id)
        .contentType(MediaType.APPLICATION_JSON);
        when(produtoServiceImpl.getById(id)).thenReturn(produtoDTO);
        //Act + Assert
        this.mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Panela"));
    }
}
