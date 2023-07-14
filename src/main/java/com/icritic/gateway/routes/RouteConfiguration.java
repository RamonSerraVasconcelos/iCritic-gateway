package com.icritic.gateway.routes;

import com.icritic.gateway.filters.AuthenticationFilter;
import com.icritic.gateway.properties.ServicesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final AuthenticationFilter authenticationFilter;

    @Autowired
    private ServicesProperties servicesProperties;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        final String USERS_SERVICE = servicesProperties.getIcriticUsersServiceAddress();

        return builder.routes()
                .route("register", r -> r.path("/register")
                        .uri(USERS_SERVICE + "/register"))

                .route("login", r -> r.path("/login")
                        .uri(USERS_SERVICE + "/login"))

                .route("refresh", r -> r.path("/refresh")
                        .uri(USERS_SERVICE + "/refresh"))

                .route("users", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(USERS_SERVICE + "/users"))
                .build();
    }
}
