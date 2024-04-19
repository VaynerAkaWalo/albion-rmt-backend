package com.albionrmtempire.service;

import com.albionrmtempire.repository.PersistedOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MetricsService {

    private final PersistedOrderRepository persistedOrderRepository;
    private long ordersLastTime = 0;

    public void logDbMetrics() {
        var count = persistedOrderRepository.count();
        var lessThan20 = persistedOrderRepository.countAllByTtlLessThan(20);
        var lessThanOrEqual0 = persistedOrderRepository.countAllByTtlLessThan(1);
        log.info("Orders in db {}, New orders {}, Not full TTL offers {}, Dead offers {}", count, count - ordersLastTime, lessThan20, lessThanOrEqual0);
        ordersLastTime = count;
    }
}
