package br.com.stock.controller;

import br.com.stock.entity.User;
import br.com.stock.model.ApiResponse;
import br.com.stock.security.jwt.JwtResponse;
import br.com.stock.security.jwt.JwtUtils;
import br.com.stock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final UserService service;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@RequestBody User loginRequest) {
        ApiResponse<JwtResponse> response = service.login(loginRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/token")
    public ResponseEntity<Boolean> checkToken(@RequestParam String token) {
        var valid = jwtUtils.validateJwtToken(token);
        return ResponseEntity.status(valid ? HttpStatus.OK.value() : HttpStatus.FORBIDDEN.value()).body(valid);
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<User>> getUserLogged(){
        ApiResponse<User> response = service.getUserLogged();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
