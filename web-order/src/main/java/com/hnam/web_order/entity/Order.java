package com.hnam.web_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    private String customerName;

    private String address;

    private String phoneNumber;

    private Double totalPrice;

    private String status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @PrePersist
    public void prePersirt(){
        this.createdAt = LocalDateTime.now();
        if(this.status == null){
            this.status = "Pending";
        }
    }
}
