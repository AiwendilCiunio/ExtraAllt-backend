package com.stocksimulator.stocksimulator.company;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "company")
@Data
@NoArgsConstructor
public class Company {

    // this constructor is needed for SeedData, removewhen SeedData no longer used
    public Company(String name, String ticker, String description,
            boolean isActive, BigDecimal pricePerShare) {
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.isActive = isActive;
        this.pricePerShare = pricePerShare;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String ticker;
    private String description;

    private boolean isActive; // only active companies will be shown to user
    private BigDecimal pricePerShare;
    // TODO: private int availableShares;

    // @OneToMany(mappedBy = "company")
    // private List<Holding> holdings;
    // @OneToMany(mappedBy = "company")
    // private List<User> users;
    // not needeD?
}
