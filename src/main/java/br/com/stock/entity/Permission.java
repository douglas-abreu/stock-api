package br.com.stock.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(schema = "tipos", name = "permissoes")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;
}
