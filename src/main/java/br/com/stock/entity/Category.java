package br.com.stock.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(schema = "tipos", name = "categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String name;

}
