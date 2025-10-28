package com.stocksimulator.stocksimulator.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stocksimulator.stocksimulator.company.Company;
import com.stocksimulator.stocksimulator.company.CompanyService;
import com.stocksimulator.stocksimulator.dto.CompanyStateDTO;
import com.stocksimulator.stocksimulator.dto.EventMessageDTO;

@Component
@EnableScheduling
public class MarketStateSimulator {

    private final SimpMessagingTemplate template;
    private final CompanyService companyService;

    public MarketStateSimulator(SimpMessagingTemplate template, CompanyService companyService) {
        this.companyService = companyService;
        this.template = template;
    }

    @Scheduled(fixedDelay = 3000)
    public void simulateAndBroadcast() {

        // set time interval between events (random)
        try {
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // TODO: (extra feature) periodically add new companies from DB
        // (companyService.activateCompany())

        // run simulation, generate messages
        List<CompanyStateDTO> changes = simulatePriceChanges();
        List<EventMessageDTO> events = generateEventMessages(changes);

        // notify message broker about changes (updates prices, ?portfolio and pushes a
        // message about event) //("topic/market")
        template.convertAndSend("/topic/market/changes", changes);
        template.convertAndSend("/topic/market/messages", events);
    }

    public List<CompanyStateDTO> simulatePriceChanges() {

        List<Company> companies = companyService.findActiveCompanyEntities();
        // decide if global/local event (40/60)
        if (Math.random() > 0.4) {
            return applyGlobalEvent(companies);
        } else {
            return applyLocalEvents(companies);
        }
    }

    public List<CompanyStateDTO> applyGlobalEvent(List<Company> companies) {
        System.out.println("Global event");
        // apply global event
        BigDecimal change = randomPercentChange(0.2);
        companies.forEach(c -> c.setPricePerShare(
                c.getPricePerShare().multiply(BigDecimal.ONE.add(change)).setScale(2, RoundingMode.HALF_UP)));
        // persist changes
        companyService.saveAll(companies);
        return companies.stream().map(c -> new CompanyStateDTO(c.getId(), c.getName(), c.getPricePerShare(), change))
                .toList();
    }

    public List<CompanyStateDTO> applyLocalEvents(List<Company> companies) {

        System.out.println("Local event");
        List<CompanyStateDTO> dtos = new ArrayList<>();

        for (Company c : companies) {
            if (Math.random() > 0.75) {
                BigDecimal change = randomPercentChange(0.1);
                c.setPricePerShare(
                        c.getPricePerShare().multiply(BigDecimal.ONE.add(change)).setScale(2, RoundingMode.HALF_UP));

                dtos.add(new CompanyStateDTO(c.getId(), c.getName(), c.getPricePerShare(), change));
            }
        }

        // persist changes
        companyService.saveAll(companies);
        return dtos;
    }

    public List<EventMessageDTO> generateEventMessages(List<CompanyStateDTO> companies) {

        List<EventMessageDTO> dtos = new ArrayList<>();
        for (CompanyStateDTO c : companies) {
            BigDecimal absolutePercentChange = c.percentChange()
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
            String direction = c.percentChange().signum() >= 0 ? "increased" : "decreased";
            String description = String.format("%s has %s by %s%%!", c.name(), direction, absolutePercentChange);
            dtos.add(new EventMessageDTO(c.name(), description, c.percentChange(), LocalDateTime.now()));
            System.out.println(description);
        }
        return dtos;
    }

    public BigDecimal randomPercentChange(double maxChange) {
        double change = (Math.random() * 2 - 1) * maxChange;
        return BigDecimal.valueOf(change);
    }
}
