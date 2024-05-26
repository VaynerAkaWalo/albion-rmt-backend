package com.albionrmtempire.consumer;

import com.albionrmtempire.datatransferobject.preprocessedorders.PreProcessedOrder;
import com.albionrmtempire.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderConsumer {

    private final MarketDataService marketDataService;
    private final Clock clock;

    @EventListener
    void processEvent(PreProcessedOrder order) {
        log.info("Order processed after {} millis", ChronoUnit.MILLIS.between(clock.instant(), order.getAcknowledgedDate()));

        marketDataService.processOrderRequest(order);
    }
}
