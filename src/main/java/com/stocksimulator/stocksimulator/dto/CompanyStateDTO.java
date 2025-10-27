package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompanyStateDTO(
                Long id,
                String name,
                BigDecimal pricePerShare,
                BigDecimal percentChange
// ,LocalDateTime lastUpdated
) {

}
