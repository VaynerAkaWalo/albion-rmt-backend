package com.albionrmtempire.config;

import com.albionrmtempire.proxy.AlbionApiClient;
import com.vaynerakawalo.springobservability.ObservabilityAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ImportAutoConfiguration(classes = ObservabilityAutoConfiguration.class)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Bean
    RestClient albionApiRestClient(@Value("${albion.api.url}") String url) {
        return RestClient.builder()
                .baseUrl(url)
                .build();
    }

    @Bean
    AlbionApiClient albionApiClient(RestClient albionApiRestClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(albionApiRestClient))
                .build()
                .createClient(AlbionApiClient.class);
    }
}
