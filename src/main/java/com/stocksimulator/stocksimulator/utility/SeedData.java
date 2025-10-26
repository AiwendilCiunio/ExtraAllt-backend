package com.stocksimulator.stocksimulator.utility;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.stocksimulator.stocksimulator.company.Company;
import com.stocksimulator.stocksimulator.company.CompanyRepository;
import com.stocksimulator.stocksimulator.user.User;
import com.stocksimulator.stocksimulator.user.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class SeedData {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public SeedData(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @PostConstruct
    public void init() {
        seedUser();
        seedCompanies();
    }

    private void seedUser() {
        userRepository.findByUsername("demo").orElseGet(() -> {
            User user = new User();
            user.setUsername("demo");
            user.setSaldo(BigDecimal.valueOf(100000));
            return userRepository.save(user);
        });
    }

    private void seedCompanies() {
        if (companyRepository.count() == 0) {

            List<Company> companies = List.of(
                    new Company("SimSoft Technologies", "SIMSFT", "Fictional software giant", false,
                            new BigDecimal("25.75")),
                    new Company("BetaEnergy", "BETA", "Renewable energy firm", true, new BigDecimal("15.40")),
                    new Company("NovaFoods", "NOVA", "Organic food producer", true, new BigDecimal("8.95")),
                    new Company("HyperLogistics", "HLOG", "AI‑driven logistics", true, new BigDecimal("42.10")),
                    new Company("QuantumFinance", "QFIN", "Fintech innovator", true, new BigDecimal("31.20")));

            companyRepository.saveAll(companies);
        }
    }
}
