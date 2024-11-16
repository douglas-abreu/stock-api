package br.com.stock.service.criteria;

import br.com.stock.entity.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class UserCriteria {
    private String keyword;
    private Integer permissionId;

    public static Specification<User> filterByUsername(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), keyword);
    }

    public static Specification<User> filterByPermission(Integer permissionId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("permission").get("id"), permissionId);
    }

}