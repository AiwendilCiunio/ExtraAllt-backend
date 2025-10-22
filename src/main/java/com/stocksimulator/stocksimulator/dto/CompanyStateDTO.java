package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompanyStateDTO(
        String ticker,
        BigDecimal price,
        BigDecimal change
// ,LocalDateTime lastUpdated
) {

}
