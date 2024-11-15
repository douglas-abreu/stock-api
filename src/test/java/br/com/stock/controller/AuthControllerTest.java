package br.com.stock.controller;

import br.com.stock.model.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RequestLogin requestLogin;

    @Test
    @DisplayName("Login Request to controller should return status 400")
    void errorAuthenticateUser() throws Exception {
        String json = "{empty}";

        var response = mockMvc.perform(
          MockMvcRequestBuilders.post("/authentication/login")
                  .content(json)
                  .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Login Request to controller should return status 200")
    void successAuthenticateUser() throws Exception {
        //ARRANGE
        BDDMockito.given(requestLogin.getUsername()).willReturn("douglas");

        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(requestLogin);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/authentication/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("LoggedUser Request should return 404")
    void errorLoggedUser() throws Exception {
        //ARRANGE
        BDDMockito.given(requestLogin.getUsername()).willReturn("");

        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(requestLogin);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.get("/authentication/logged")
                        .header("Authorization",
                                "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkb3VnbGFzIiwiaWF0IjoxNzMwMTIy" +
                                        "NzkyLCJleHAiOjE3MzAxMzcxOTJ9.qFJOlBPtkAYNNPYYf5Ry_0W8kABfPKfWKMx0VnAXb0mhNJNWHmCxr5Ex" +
                                        "Aciv2UnmQ8H1Ov6itNGSs7Y0XZDrG5")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("LoggedUser Request should return 226")
    void successLoggedUser() throws Exception {
        //ARRANGE
        BDDMockito.given(requestLogin.getUsername()).willReturn("douglas");

        //ACT
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(requestLogin);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.get("/authentication/logged")
                        .header("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkb3VnbGFzIiwiaWF0IjoxNzMwMTIy" +
                        "NzkyLCJleHAiOjE3MzAxMzcxOTJ9.qFJOlBPtkAYNNPYYf5Ry_0W8kABfPKfWKMx0VnAXb0mhNJNWHmCxr5Ex" +
                        "Aciv2UnmQ8H1Ov6itNGSs7Y0XZz7ZA")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(226, response.getStatus());
    }
}