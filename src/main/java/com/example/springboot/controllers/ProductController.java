package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController // Annotation que expoe os endpoints(atribudos) para acesso externo
public class ProductController { // Início da classe ProductController

    @Autowired // Annotation para realizar uma injeção de dependência automática em classes gerenciadas pelo contêiner Spring.
    ProductRepository productRepository;

    @PostMapping("/products") // Annotation que serve para cadastrar(criar e salvar) um produto
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){ // Método saveProduct com argumento productRecordDto com anotação para validação
        var productModel = new ProductModel(); // declarando o ProductModel
        BeanUtils.copyProperties(productRecordDto, productModel); // BeanUtil serve para converter o productRecordDto para o productModel
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel)); // está retornando o código Http 200
    }

    @GetMapping("/products") // Annotation que serve para consultar(ler) todos os produtos
    public ResponseEntity<List<ProductModel>> getAllProducts(){ // Método getAllProducts
        List<ProductModel> productsList = productRepository.findAll();
        if (!productsList.isEmpty()){
            for (ProductModel product : productsList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    @GetMapping("/products/{id}") // Annotation que serve para consultar(ler) um determinado produto
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id){ // Método getOneProduct
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }

    @PutMapping("/products/{id}") // Annotation que serve para atualizar(update) um produto
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto){ // Método que recebe argumentos para atualizar o produto
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not foud.");
        }
        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}") // Método que serve para deletar um determinado produto
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}
