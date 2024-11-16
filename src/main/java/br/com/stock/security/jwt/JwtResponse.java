package br.com.stock.security.jwt;


import br.com.stock.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Integer id;
    private String username;
    private Permission permission;
}
