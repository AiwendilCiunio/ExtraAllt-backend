package com.stocksimulator.stocksimulator.dto;

import java.math.BigDecimal;

public record HoldingUpdateDTO(
    long id,
    int quantity,
    BigDecimal pricePerShare
) {

}
