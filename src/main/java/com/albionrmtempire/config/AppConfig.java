package com.albionrmtempire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.AsyncTaskExecutor;

import java.time.Clock;

@Configuration
public class AppConfig {


    @Bean
    ApplicationEventMulticaster applicationEventMulticaster(AsyncTaskExecutor taskExecutor) {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(taskExecutor);

        return eventMulticaster;
    }

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
