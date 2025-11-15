package com.example.ganzi6.domain.market.Market;

import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.user.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @Column(length = 200)
    private String description;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Product> products = new ArrayList<>();
}