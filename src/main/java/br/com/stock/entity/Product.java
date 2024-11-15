package br.com.stock.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "entidades", name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_barra")
    private String barCode;

    @Column(name = "quantidade")
    private int quantity;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "fabricante_id")
    private Manufacturer manufacturer;

}
