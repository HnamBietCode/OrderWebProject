package com.hnam.web_order.security.service;


import com.hnam.web_order.entity.Cart;
import com.hnam.web_order.entity.CartItem;
import com.hnam.web_order.entity.Product;
import com.hnam.web_order.entity.User;
import com.hnam.web_order.repository.CartRepository;
import com.hnam.web_order.repository.ProductRepository;
import com.hnam.web_order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    //lấy giỏ hàng user khi login( chưa có thì tạo mới)
    public Cart getCarByUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("không tìm thấy người dùng"));

        return cartRepository.findByUser(user).orElseGet(()-> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Transactional
    public void addToCart(String username, Long productId, int quantity){
        Cart cart = getCarByUser(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(String username, Long productId) {
        Cart cart = getCarByUser(username);
        // Xóa item có productId tương ứng
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    //Xóa sạch giỏ hàng (Dùng khi Checkout xong)
    @Transactional
    public void clearCart(String username) {
        Cart cart = getCarByUser(username);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
