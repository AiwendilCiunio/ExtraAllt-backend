package com.stocksimulator.stocksimulator.company;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "company")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String ticker;
    private String description;

    private boolean isActive; // only active companies will be shown to user
    private BigDecimal pricePerShare;

    // @OneToMany(mappedBy = "company")
    // private List<Holding> holdings;
    // @OneToMany(mappedBy = "company")
    // private List<User> users;
    // not needeD?
}
