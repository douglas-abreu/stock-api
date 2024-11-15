package br.com.stock.controller;

import br.com.stock.model.ApiResponse;
import br.com.stock.model.JWTResponse;
import br.com.stock.model.RequestLogin;
import br.com.stock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> authenticateUser(
            @RequestBody RequestLogin requestLogin) {
        ApiResponse<JWTResponse> response = service.authenticateUser(requestLogin);
        return ResponseEntity.status(response.getStatus()).headers(response.getHeaders()).body(response);
    }

    @PatchMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logoutUser() {
        ApiResponse<Boolean> response = service.revokeToken();
        return ResponseEntity.status(response.getStatus()).headers(response.getHeaders()).body(response);
    }

    @GetMapping("/logged")
    public ResponseEntity<ApiResponse<?>> authenticateUser(
            @RequestHeader("Authorization") String token) {
        ApiResponse<JWTResponse> response = service.getUserLogged(
                token.replace("Bearer ", ""));
        return ResponseEntity.status(response.getStatus()).headers(response.getHeaders()).body(response);
    }

}
