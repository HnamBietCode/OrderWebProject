package com.hnam.web_order.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    private Double price;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

}