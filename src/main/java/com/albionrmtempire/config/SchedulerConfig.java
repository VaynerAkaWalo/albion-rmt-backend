package com.albionrmtempire.config;

import com.albionrmtempire.service.MarketDataService;
import com.albionrmtempire.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final MetricsService metricsService;
    private final MarketDataService marketDataService;

    @Scheduled(fixedDelay = 5000)
    @CacheEvict("orders")
    public void evictCache() {}

    @Scheduled(fixedDelay = 20000)
    void emitDbMetrics() {
        metricsService.logDbMetrics();
    }

    @Scheduled(cron = "@hourly")
    void reduceTtl() {
        marketDataService.adjustOffersTtl();
    }
}
