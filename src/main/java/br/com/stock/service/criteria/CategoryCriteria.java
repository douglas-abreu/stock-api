package br.com.stock.service.criteria;

import br.com.stock.entity.Category;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class CategoryCriteria {
    private String keyword;

    public static Specification<Category> filterByName(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), '%'+keyword+'%');
    }

}