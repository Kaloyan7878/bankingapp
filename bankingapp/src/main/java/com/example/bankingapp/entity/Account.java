package com.example.bankingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //increment for each new row
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY) //load only the account details(LAZY), many accounts for 1 user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
