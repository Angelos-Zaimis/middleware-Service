package com.middleware_service.middleware_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order_products")
@AllArgsConstructor
@NoArgsConstructor
public class Order_Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "sku", nullable = false)
    private Integer sku;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}
