package com.middleware_service.middleware_service.entity;


import com.middleware_service.middleware_service.enums.Order_status;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Order_status status;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order")
    Set<Order_Product> products;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "vat")
    private Double vat;
}
