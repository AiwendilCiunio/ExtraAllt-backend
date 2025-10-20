package com.stocksimulator.stocksimulator.user;

import java.util.List;

import com.stocksimulator.stocksimulator.company.Company;
import com.stocksimulator.stocksimulator.holding.Holding;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<Company> shares;
    // not needed?

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<Holding> holdings;
    // not needed?
}
