package com.SlicK.api_gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;

@Configuration
public class GatewayConfig {
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("person-service", r -> r.path("/BASIC-CRUD/**")
                        .filters(f -> f.stripPrefix(1).filter(filter))
                        .uri("lb://basic-crud"))
                .build();
    }
}