package com.albionrmtempire.config;

import com.albionrmtempire.provider.AlbionApiProvider;
import com.albionrmtempire.service.MarketDataService;
import com.albionrmtempire.service.MetricsService;
import com.albionrmtempire.service.crystalleague.CrystalMatchService;
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
    private final AlbionApiProvider albionApiProvider;
    private final CrystalMatchService crystalMatchService;

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

    @Scheduled(cron = "*/5 * * * *")
    void getLatestCrystalMatches() {
        final var matches = albionApiProvider.getLatestCrystalMatches();

        matches.forEach(crystalMatchService::persistMatchResults);
    }
}
