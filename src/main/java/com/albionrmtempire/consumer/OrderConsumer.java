package com.albionrmtempire.consumer;

import com.albionrmtempire.dataobject.Order;
import com.albionrmtempire.dataobject.PersistedOrder;
import com.albionrmtempire.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderConsumer {

    private final MarketDataService marketDataService;
    private final Clock clock;

    @EventListener
    void processEvent(Order order) {
        final long durationInQueue = calcMillisBetweenNow(order.acknowledgedDate());
        log.info("Order processed after {} seconds, {} millis", TimeUnit.MILLISECONDS.toSeconds(durationInQueue), durationInQueue % 1000);

        marketDataService.processOrder(toPersistedOrder(order));
    }

    private PersistedOrder toPersistedOrder(Order order) {
        return PersistedOrder.builder()
                .orderId(order.id())
                .amount(order.amount())
                .unitPrice(order.unitPrice())
                .tier(order.tier())
                .enchant(order.enchant())
                .quality(order.quality())
                .expire(order.expire())
                .item(AggregateReference.to(order.item().systemName()))
                .build();
    }

    private long calcMillisBetweenNow(Date then) {
        return clock.millis() - then.toInstant().toEpochMilli();
    }
}
