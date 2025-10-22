package com.stocksimulator.stocksimulator.utility;

import java.math.BigDecimal;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MarketStateSimulator {

    @Scheduled(fixedDelay = 3000)
    public void simulate() {

        // set time interval between events (random)
        try {
            Thread.sleep((long) Math.random() * 3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // get all active companies from DB (inject Company service/repo)

        // decide randomly if global/local event
        // global: applyGlobalEvent();
        // local: applyLocalEvent();

        // persist changes

        // notify message broker about changes (updates prices, ?portfolio and pushes a
        // message about event) //("topic/market")

    }

    public BigDecimal randomPercentChange(double maxChange) {
        return null;
    }
}
