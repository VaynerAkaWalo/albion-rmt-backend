package com.albionrmtempire.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Scheduled(fixedDelay = 5000)
    @CacheEvict("cache")
    public void evictCache() {}
}
