package com.example.springboot.repositories;

import com.example.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository // Annotation que faz o link da tabela ProductModel com a classe ProductModel
public interface ProductRepository extends JpaRepository<ProductModel, UUID> { // Interface ProductRepository extendendo JpaRepository
}
