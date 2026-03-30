package com.riu.hotel.bootstrap.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@Profile("!test")
@EntityScan(basePackages = "com.riu.hotel.adapter")
@EnableJpaRepositories(basePackages = "com.riu.hotel.adapter")
public class JpaAdapterConfiguration {
}
