package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HoldingCreateDTO(
        @NotBlank(message = "Company name is required") String companyName,
        @NotNull(message = "Quantity cannot be null") int quantity,
        @NotNull(message = "Price per share cannot be null") BigDecimal pricePerShare) {

}
