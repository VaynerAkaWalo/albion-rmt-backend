package com.albionrmtempire.consumer;

import com.albionrmtempire.dataobject.Order;
import com.albionrmtempire.dataobject.PersistedOrder;
import com.albionrmtempire.repository.PersistedOrderRepository;
import com.albionrmtempire.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderConsumer {

    private static final ZoneId UTC = ZoneOffset.UTC;
    private final MarketDataService marketDataService;

    @EventListener
    void processEvent(Order order) {
        final long durationInQueue = calcSecondsFrom(order.acknowledgedDate());
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
                .expire(Date.valueOf(order.expire().toLocalDate()))
                .item(AggregateReference.to(order.item().systemName()))
                .build();
    }

    private long calcSecondsFrom(ZonedDateTime date){
        Instant duration = ZonedDateTime.now(UTC).toInstant().minus(date.toEpochSecond(), ChronoUnit.SECONDS);
        return duration.toEpochMilli();
    }
}
