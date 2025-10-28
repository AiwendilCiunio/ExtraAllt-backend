package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.stocksimulator.stocksimulator.holding.Holding;

public record HoldingDTO(
        long id,
        int quantity,
        BigDecimal averagePrice,
        LocalDateTime lastUpdated,
        CompanyDTO company) {

    public static HoldingDTO fromEntity(Holding h) {
        return new HoldingDTO(h.getId(),
                h.getQuantity(),
                h.getAveragePrice(),
                h.getLastUpdated(),
                CompanyDTO.fromEntity(h.getCompany()));
    }

    public static List<HoldingDTO> fromEntities(List<Holding> holdings) {
        return holdings.stream().map(HoldingDTO::fromEntity).toList();
    }
}
