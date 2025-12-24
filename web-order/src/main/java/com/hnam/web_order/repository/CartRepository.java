package com.hnam.web_order.repository;

import com.hnam.web_order.entity.Cart;
import com.hnam.web_order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}