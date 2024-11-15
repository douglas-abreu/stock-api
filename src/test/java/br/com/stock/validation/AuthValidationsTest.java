package br.com.stock.validation;

import br.com.stock.exceptions.RequestLoginException;
import br.com.stock.model.RequestLogin;
import br.com.stock.security.jwt.JwtUtils;
import br.com.stock.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthValidationsTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private RequestLogin requestLogin;


    @Test
    @DisplayName("Should not throw Expection in RequestLogin Validation")
    void validLogin() {
        //ARRANGE
        BDDMockito.given(requestLogin.getUsername()).willReturn("douglas");

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> userService.authenticateUser(requestLogin));
    }

    @Test
    @DisplayName("Should throw RequestLoginExpection when username is null")
    void nullUsername() {
        //ASSERT + ACT
        Assertions.assertThrows(RequestLoginException.class,() -> userService.authenticateUser(requestLogin));
    }

    @Test
    @DisplayName("Should not throw expection with UserLogged request with a valid token")
    void validToken() {
        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> userService.getUserLogged(
                "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkb3VnbGFzIiwiaWF0" +
                        "IjoxNzI5NzAyMzA1LCJleHAiOjE3Mjk3MTY3MDV9.uI8rGaTNpvlYf6fBjCdnLWiRwweCET63jGcfaDxTqOlaRoFKFOQgIQ" +
                        "8EM7wFEZ4l-aWz45Yo9QfPygvjCIy53c"));
    }
}