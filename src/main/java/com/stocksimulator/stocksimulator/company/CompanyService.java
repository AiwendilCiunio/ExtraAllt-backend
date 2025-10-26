package com.stocksimulator.stocksimulator.company;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stocksimulator.stocksimulator.dto.CompanyDTO;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // public Company activateCompany(Company company)
    // sets isActive to true

    // used by frontend
    // gets all companies with isActive = true
    public List<CompanyDTO> findActiveCompanies() {
        return CompanyDTO.fromEntities(companyRepository.findByIsActive(true));
    }

    // used by MarketStateSimulator
    public List<Company> findActiveCompanyEntities() {
        return companyRepository.findByIsActive(true);
    }

    public CompanyDTO getById(long id) {
        return companyRepository.findById(id).map(CompanyDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("company not found"));
    }

    // save all companies (after price changes)
    public List<Company> saveAll(List<Company> companies) {
        return companyRepository.saveAll(companies);
    }

    // user to create holding
    public Company getByName(String company) {
        return companyRepository.findByName(company).orElseThrow(() -> new RuntimeException("company not found"));
    }

}
