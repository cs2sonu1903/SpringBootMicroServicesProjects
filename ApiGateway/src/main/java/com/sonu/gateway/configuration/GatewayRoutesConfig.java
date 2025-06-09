package com.sonu.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service", r -> r.path("/users/**")
                        .uri("lb://USER-SERVICE"))

                .route("hotel-service", r -> r.path("/hotels/**", "/staffs/**")
                        .uri("lb://HOTEL-SERVICE"))

                .route("rating-service", r -> r.path("/ratings/**")
                        .uri("lb://RATING-SERVICE"))

                .build();
    }
}
