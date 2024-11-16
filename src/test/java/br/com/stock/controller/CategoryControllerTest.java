package br.com.stock.controller;

import br.com.stock.entity.Category;
import br.com.stock.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Transactional
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Mock
    private Category category;

    @Test
    @DisplayName("Create Category with empty object should return status 400")
    void saveError() throws Exception {
        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(category);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/category")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Update Category with invalid fields should return status 400")
    void updateError() throws Exception {
        //ARRANGE
        String json = "{\"empty-object\":\"empty-object\"}";

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.put("/category")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Delete Category with repository not finding register should return status 404")
    void deleteError() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getId()).willReturn(1);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(Optional.empty());

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Create Category with correct fields should return status 200")
    void saveSuc() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getName()).willReturn("Test Category");

        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(category);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/category")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(201, response.getStatus());
    }


    @Test
    @DisplayName("Update Category with correct fields and finding register should return status 200")
    void updateSuccess() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getId()).willReturn(1);
        String json = "{" +
                    "\"id\": 1," +
                    "\"name\": \"Test Test Category\"" +
                "}";
        BDDMockito.given(categoryRepository.existsById(category.getId())).willReturn(true);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(
                Optional.of(new Category(1,"Test Category")));

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
    @DisplayName("Delete Category finding register should return status 200")
    void deleteSuccess() throws Exception {
        //ARRANGE
        BDDMockito.given(category.getId()).willReturn(1);
        BDDMockito.given(categoryRepository.findById(category.getId())).willReturn(
                Optional.of(new Category(1,"Test Category")));

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }


}