package com.hnam.web_order.repository;
import com.hnam.web_order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    // để trống spring tạo các method để xài
}
