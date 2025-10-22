package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompanyStateDTO(
        String name,
        BigDecimal price,
        BigDecimal percentChange
// ,LocalDateTime lastUpdated
) {

}
