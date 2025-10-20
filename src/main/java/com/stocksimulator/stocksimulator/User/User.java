package com.stocksimulator.stocksimulator.User;

import java.util.List;

import com.stocksimulator.stocksimulator.Company.Company;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Company> shares;
}
