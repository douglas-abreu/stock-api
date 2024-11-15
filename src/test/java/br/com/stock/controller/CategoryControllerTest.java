package br.com.stock.controller;

import br.com.stock.entity.Category;
import br.com.stock.entity.Permission;
import br.com.stock.entity.User;
import br.com.stock.repository.CategoryRepository;
import br.com.stock.security.jwt.JwtUtils;
import br.com.stock.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private User user;

    @Mock
    private Permission permission;

    @Mock
    private Category category;



    @Test
    @DisplayName("Create Category should return status 400")
    void saveError() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getName()).willReturn("");
        BDDMockito.given(user.getPermission()).willReturn(permission);
        BDDMockito.given(permission.getId()).willReturn(1);
        BDDMockito.given(permission.getName()).willReturn("Administrador");

        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(category);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/category/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Update Category should return status 400")
    void updateError() throws Exception {
        //ARRANGE
        String json = "{\"empty-object\":\"empty-object\"}";

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.put("/category/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Delete Category should return status 400")
    void deleteError() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getId()).willReturn(1);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(null);

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Create Category should return status 200")
    void saveSuc() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getName()).willReturn("Test Category");
        BDDMockito.given(user.getPermission()).willReturn(permission);
        BDDMockito.given(permission.getId()).willReturn(1);
        BDDMockito.given(permission.getName()).willReturn("Administrador");

        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(category);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/category/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }


    @Test
    @DisplayName("Update Category should return status 200")
    void updateSuccess() throws Exception {
        //ARRANGE
        String json = "{" +
                    "\"id\": 1," +
                    "\"name\": \"Test Category\"" +
                "}";

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.put("/category/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Delete Category should return status 200")
    void deleteSuccess() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getId()).willReturn(1);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(Optional.of(new Category(1,"Test Category")));
        BDDMockito.given(categoryRepository.existsById(category.getId())).willReturn(true);

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }


}