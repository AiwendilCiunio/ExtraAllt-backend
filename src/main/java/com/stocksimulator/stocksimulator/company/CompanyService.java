package com.stocksimulator.stocksimulator.company;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // public Company activateCompany(Company company)
    // sets isActive to true

    // gets all companies with isActive = true
    public List<Company> findActiveCompanies() {
        return companyRepository.findByIsActive(true);
    }

    // save all companies (after price changes)
    public List<Company> saveAll(List<Company> companies) {
        return companyRepository.saveAll(companies);
    }

}
