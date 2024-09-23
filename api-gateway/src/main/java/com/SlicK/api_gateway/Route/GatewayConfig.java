package com.SlicK.api_gateway.Route;

import com.SlicK.api_gateway.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .route("soap-demo1", r -> r.path("/SOAP-DEMO1/**")
                        .filters(f -> f.stripPrefix(1).filter(filter))
                        .uri("lb://soap-demo1"))
                .route("soap-demo2", r -> r.path("/SOAP-DEMO2/**")
                        .filters(f -> f.stripPrefix(1).filter(filter))
                        .uri("lb://soap-demo2"))
                .build();
    }
}