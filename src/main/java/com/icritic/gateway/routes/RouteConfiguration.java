package com.icritic.gateway.routes;

import com.icritic.gateway.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("login", r -> r.path("/login")
                        .uri("http://localhost:8081/login"))

                .route("refresh", r -> r.path("/refresh")
                        .uri("http://localhost:8081/refresh"))

                .route("users", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8081/users"))
                .build();
    }
}
