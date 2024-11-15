package br.com.stock.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {
    private String login;
    private String name;
    private Boolean authenticated;
}
