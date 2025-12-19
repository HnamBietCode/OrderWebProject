package com.hnam.web_order.controller;

import com.hnam.web_order.entity.Product;
import com.hnam.web_order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //đây là nơi nhận api nha
@RequestMapping("/api/products") //đường dẫn chung: http://localhost:8080/api/products
public class ProductController {
    @Autowired //tự động tiêm Repo vào để dùng ( gọi là Dependency Injection)
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

}

