package com.hnam.web_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.hnam.web_order.entity.Product;

@Entity
@Table(name="orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String customerName;

    private String address;

    private String phoneNumer;

    private Double totalPrice;

    private String status;

    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @PrePersist
    public void prePersirt(){
        this.createAt = LocalDateTime.now();
        if(this.status == null){
            this.status = "Pending";
        }
    }

}
