package com.stocksimulator.stocksimulator.holding;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.stocksimulator.stocksimulator.company.Company;
import com.stocksimulator.stocksimulator.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "holding")
@Data
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal quantity;
    private BigDecimal value;

    private LocalDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}
