package com.stocksimulator.stocksimulator.holding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import com.stocksimulator.stocksimulator.company.Company;
import com.stocksimulator.stocksimulator.company.CompanyService;
import com.stocksimulator.stocksimulator.dto.HoldingCreateDTO;
import com.stocksimulator.stocksimulator.dto.HoldingUpdateDTO;
import com.stocksimulator.stocksimulator.user.User;

public class HoldingService {

    private final HoldingRepository holdingRepository;
    private final CompanyService companyService;

    public HoldingService(HoldingRepository holdingRepository, CompanyService companyService) {
        this.holdingRepository = holdingRepository;
        this.companyService = companyService;
    }

    public List<Holding> getAllHoldings() {
        return holdingRepository.findAll();
    }

    public Holding createHolding(HoldingCreateDTO dto, User user) {
        Company company = companyService.getByName(dto.company());

        Holding holding = Holding.builder()
                .user(user)
                .company(company)
                .quantity(dto.quantity())
                .averagePrice(dto.pricePerShare())
                .lastUpdated(LocalDateTime.now())
                .build();

        return holdingRepository.save(holding);
    }

    public Holding updateHolding(HoldingUpdateDTO dto) {
        Holding holding = holdingRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("holding not found"));

        BigDecimal oldAvg = holding.getAveragePrice();
        int oldQty = holding.getQuantity();
        int newQty = dto.quantity();

        BigDecimal qtyOld = BigDecimal.valueOf(oldQty);
        BigDecimal qtyNew = BigDecimal.valueOf(newQty);

        BigDecimal newAvg = oldAvg.multiply(qtyOld)
                .add(dto.pricePerShare().multiply(qtyNew))
                .divide(qtyOld.add(qtyNew), 2, RoundingMode.HALF_UP);

        holding.setAveragePrice(newAvg);
        holding.setQuantity(oldQty + newQty);
        holding.setLastUpdated(LocalDateTime.now());

        return holdingRepository.save(holding);

    }

}
