package br.com.stock.controller;

import br.com.stock.entity.Product;
import br.com.stock.entity.Product;
import br.com.stock.model.ApiResponse;
import br.com.stock.model.PaginatedData;
import br.com.stock.service.ProductService;
import br.com.stock.service.ProductService;
import br.com.stock.service.criteria.ProductCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Product>> save(@RequestBody Product user) {
        ApiResponse<Product> response = service.saveProduct(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<Product>> update(@RequestBody Product user) {
        ApiResponse<Product> response = service.updateProduct(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PaginatedData<Product>>> getAll(@PageableDefault Pageable pageable, ProductCriteria criteria) {
        ApiResponse<PaginatedData<Product>> response = service.productList(pageable, criteria);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Integer id){
        ApiResponse<?> amendmentApiResponse = service.deleteProduct(id);
        return ResponseEntity.status(amendmentApiResponse.getStatus()).body(amendmentApiResponse);
    }




}
