package br.com.stock.controller;

import br.com.stock.entity.Category;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.PaginatedData;
import br.com.stock.service.CategoryService;
import br.com.stock.service.criteria.CategoryCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {

    private final CategoryService service;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Category>> save(@RequestBody Category user) {
        ApiResponse<Category> response = service.saveCategory(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<Category>> update(@RequestBody Category user) {
        ApiResponse<Category> response = service.updateCategory(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping ("")
    public ResponseEntity<ApiResponse<PaginatedData<Category>>> getAll(@PageableDefault Pageable pageable, CategoryCriteria criteria) {
        ApiResponse<PaginatedData<Category>> response = service.categoryList(pageable, criteria);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable Integer id){
        ApiResponse<?> response = service.deleteCategory(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
