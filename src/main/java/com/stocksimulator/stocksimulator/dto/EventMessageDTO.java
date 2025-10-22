package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventMessageDTO(
        String company,
        String eventDescription,
        BigDecimal percentChange,
        LocalDateTime timestamp) {

}
