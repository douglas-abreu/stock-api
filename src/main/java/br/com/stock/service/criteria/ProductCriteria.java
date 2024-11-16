package br.com.stock.service.criteria;

import br.com.stock.entity.Product;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ProductCriteria {
    private String keyword;
    private Integer categoryId;

    public static Specification<Product> filterByName(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), '%'+keyword+'%');
    }

    public static Specification<Product> filterByDescription(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), '%'+keyword+'%');
    }

    public static Specification<Product> filterByBarCode(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("barCode"), '%'+keyword+'%');
    }

    public static Specification<Product> filterByCategoryId(Integer categoryId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

}