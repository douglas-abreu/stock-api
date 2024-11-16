package br.com.stock.controller;

import br.com.stock.entity.User;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.PaginatedData;
import br.com.stock.service.UserService;
import br.com.stock.service.criteria.UserCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> save(@RequestBody User user) {
        ApiResponse<User> response = service.saveUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<User>> update(@RequestBody User user) {
        ApiResponse<User> response = service.updateUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping ("/list")
    public ResponseEntity<ApiResponse<PaginatedData<User>>> getAll(@PageableDefault Pageable pageable, UserCriteria criteria) {
        ApiResponse<PaginatedData<User>> response = service.userList(pageable, criteria);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
