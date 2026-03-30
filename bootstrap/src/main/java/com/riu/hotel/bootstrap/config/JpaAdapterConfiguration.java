package com.riu.hotel.bootstrap.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Escaneo de entidades y repositorios JPA bajo {@code com.riu.hotel.adapter}.
 * La clase principal vive en {@code com.riu.hotel.bootstrap}; sin esto, Spring Data no encuentra los repositorios.
 * El perfil {@code test} queda excluido para permitir tests de contexto sin EMF/JPA.
 */
@Configuration
@Profile("!test")
@EntityScan(basePackages = "com.riu.hotel.adapter")
@EnableJpaRepositories(basePackages = "com.riu.hotel.adapter")
public class JpaAdapterConfiguration {
}
