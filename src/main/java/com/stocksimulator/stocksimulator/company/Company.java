package com.stocksimulator.stocksimulator.company;

import java.util.List;

import com.stocksimulator.stocksimulator.holding.Holding;
import com.stocksimulator.stocksimulator.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    // @OneToMany(mappedBy = "company")
    // private List<Holding> holdings;
    // @OneToMany(mappedBy = "company")
    // private List<User> users;
    // not needeD?
}
