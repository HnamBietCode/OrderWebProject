package com.hnam.web_order.controller;

import com.hnam.web_order.entity.Product;
import com.hnam.web_order.repository.ProductRepository;
import com.hnam.web_order.security.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //đây là nơi nhận api nha
@RequestMapping("/api/products") //đường dẫn chung: http://localhost:8080/api/products
public class ProductController {
    @Autowired //tự động tiêm Repo vào để dùng ( gọi là Dependency Injection)
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // API: Thêm vào giỏ hàng
    // POST /api/cart/add?productId=1&quantity=1
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long productId,
                                       @RequestParam(defaultValue = "1") int quantity) {

        // Lấy tên người dùng đang đăng nhập từ Token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        cartService.addToCart(username, productId, quantity);

        return ResponseEntity.ok("Đã thêm vào giỏ hàng thành công!");
    }

}

