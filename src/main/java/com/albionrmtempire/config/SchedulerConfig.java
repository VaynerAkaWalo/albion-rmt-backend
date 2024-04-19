package com.albionrmtempire.config;

import com.albionrmtempire.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final MetricsService metricsService;

    @Scheduled(fixedDelay = 5000)
    @CacheEvict("orders")
    public void evictCache() {}

    @Scheduled(fixedDelay = 20000)
    void emitDbMetrics() {
        metricsService.logDbMetrics();
    }
}
