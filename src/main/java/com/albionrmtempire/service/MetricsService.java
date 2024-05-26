package com.albionrmtempire.service;

import com.albionrmtempire.provider.OrderProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MetricsService {

    private final OrderProvider orderProvider;
    private long ordersLastTime = 0;

    public void logDbMetrics() {
        var count = orderProvider.ordersCount();
        var notFullTtlOrders = orderProvider.notFullTtlOrders();
        var deadOffers = orderProvider.deadOrders();
        log.info("Orders in db {}, New orders {}, Not full TTL offers {}, Dead offers {}", count, count - ordersLastTime, notFullTtlOrders, deadOffers);
        ordersLastTime = count;
    }
}
