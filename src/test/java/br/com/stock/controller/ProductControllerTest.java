package br.com.stock.controller;

import br.com.stock.entity.Category;
import br.com.stock.entity.Permission;
import br.com.stock.entity.Product;
import br.com.stock.entity.User;
import br.com.stock.repository.CategoryRepository;
import br.com.stock.repository.ProductRepository;
import br.com.stock.security.jwt.JwtUtils;
import br.com.stock.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Transactional
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @Mock
    private User user;

    @Mock
    private Permission permission;

    @Mock
    private Category category;

    @Mock
    private Product product;

    @Test
    @DisplayName("Create Product should return status 400")
    void saveError() throws Exception {
        //ACT
        var om = new ObjectMapper();
        var json = om.writeValueAsString(product);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Update Product should return status 400")
    void updateError() throws Exception {
        //ARRANGE
        String json = "{" +
                "\"id\": 1, " +
                "\"name\": \"\", " +
                "\"barCode\": \"\", " +
                "\"description\": \"\", " +
                "\"quantity\": 200, " +
                "\"category\": { \"id\": 1, \"name\": \"category\"}" +
                "}";
        BDDMockito.given(category.getId()).willReturn(1);
        BDDMockito.given(categoryRepository.existsById(category.getId())).willReturn(true);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(Optional.of(new Category(1,"Test Category")));

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.put("/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Delete Product not existent register should return status 400")
    void deleteError() throws Exception {
        //ARRANGE
        BDDMockito.given(product.getId()).willReturn(1);
        BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Create Product should return status 200")
    void saveSuc() throws Exception {
        //ARRANGE
        String json = "{" +
                "\"id\": 1, " +
                "\"name\": \"Test Product\", " +
                "\"barCode\": \"111111111111\", " +
                "\"description\": \"Test Product\", " +
                "\"quantity\": 200, " +
                "\"category\": { \"id\": 1, \"name\": \"category\"}" +
                "}";
        //ACT

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }


    @Test
    @DisplayName("Update Product with correct fields should return status 200")
    void updateSuccess() throws Exception {
        //ARRANGE
        String json = "{" +
                "\"id\": 1, " +
                "\"name\": \"Test Product\", " +
                "\"barCode\": \"111111111111\", " +
                "\"description\": \"Test Product\", " +
                "\"quantity\": 200, " +
                "\"category\": { \"id\": 1, \"name\": \"category\"}" +
                "}";
        BDDMockito.given(category.getId()).willReturn(1);
        BDDMockito.given(categoryRepository.existsById(category.getId())).willReturn(true);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(Optional.of(new Category(1,"Test Category")));

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.put("/category")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Delete Product with existent register should return status 200")
    void deleteSuccess() throws Exception {
        //ARRANGE
        BDDMockito.given(product.getId()).willReturn(1);
        BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.of(new Product()));
        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/"+product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

}