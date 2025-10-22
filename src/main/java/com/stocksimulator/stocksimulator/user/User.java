package com.stocksimulator.stocksimulator.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    // TODO: password
    // TODO: email

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<Company> shares;
    // not needed?

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<Holding> holdings;
    // not needed?
}
