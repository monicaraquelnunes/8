package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity // Annotation que liga(mapeia) essa classe com a tabela no banco
@Table(name = "TB_PRODUCTS") // Annotation especificando o nome da nossa tabela
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable { // Refere-se ao início da classe ProductModel extendendo RepresentationModel e implementando o Serializable
    private static final long serialVersionUID = 1L; // é o número de controle de inversão das classe que serão serializadas

    @Id // Annotation que mapeia a coluna Id do banco com o atributo id da classe
    @GeneratedValue(strategy = GenerationType.AUTO) // Annotation que gera uma numeração automática no banco
    private UUID idProduct; // atributo idProduct, do tipo UUID recomendado para esse tipo de arquitetura distribuída
    private String name; // atributo name, do tipo String
    private BigDecimal value; // atributo value do tipo BigDecipal

    public UUID getIdProduct() {
        return idProduct;
    } // métodos get

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    } // métodos set

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
