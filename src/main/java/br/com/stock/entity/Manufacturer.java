package br.com.stock.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(schema = "entidades", name = "fabricantes")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome_fantasia")
    private String fantasyName;

    @Column(name = "cnpj")
    private String cnpj;

}
