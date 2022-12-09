package com.ironhackproject.bank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal balance;

    @Column(name = "secret_key")
    private String secretKey;

    @JoinColumn(name = "primary_owner")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User primaryOwner;

    @JoinColumn(name = "secondary_owner")
    @ManyToOne(fetch = FetchType.LAZY)
    private User secondaryOwner;

    @Column(name = "minimum_balance")
    private BigDecimal minimumBalance;

    @Column(name = "penalty_fee")
    private BigDecimal penaltyFee;

    @Column(name = "monthly_maintenance_fee")
    private  BigDecimal monthlyMaintenanceFee;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "interest_added_date")
    private LocalDateTime interestAddedDate;

}
