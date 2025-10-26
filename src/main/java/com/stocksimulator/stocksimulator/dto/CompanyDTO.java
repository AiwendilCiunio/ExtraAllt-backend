package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;
import java.util.List;

import com.stocksimulator.stocksimulator.company.Company;

public record CompanyDTO(
        Long id,
        String name,
        String ticker,
        String description,
        BigDecimal pricePerShare) {

    public static CompanyDTO fromEntity(Company company) {
        return new CompanyDTO(company.getId(),
                company.getName(),
                company.getTicker(),
                company.getDescription(),
                company.getPricePerShare());
    }

    public static List<CompanyDTO> fromEntities(List<Company> companies) {
        return companies.stream().map(CompanyDTO::fromEntity).toList();
    }
}
