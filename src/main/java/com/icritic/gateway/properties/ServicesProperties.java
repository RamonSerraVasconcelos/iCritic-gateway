package com.icritic.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.properties.services")
@Getter
@Setter
public class ServicesProperties {

    private String icriticUsersServiceAddress;
    private String icriticMoviesServiceAddress;
}
