package com.riu.hotel.application.config;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.riu.hotel.domain.port.in.CountEqualSearchesUseCase;
import com.riu.hotel.domain.port.in.PublishAvailabilitySearchUseCase;
import com.riu.hotel.domain.port.in.RegisterAvailabilitySearchUseCase;
import com.riu.hotel.domain.port.out.AvailabilitySearchEventPublisherPort;
import com.riu.hotel.domain.port.out.AvailabilitySearchPersistencePort;
import com.riu.hotel.domain.port.out.AvailabilitySearchQueryPort;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class UseCaseConfigurationTest {

    @Test
    void shouldRegisterUseCaseBeans() {
        try (var ctx = new AnnotationConfigApplicationContext()) {
            ctx.getBeanFactory().registerSingleton(
                    "availabilitySearchEventPublisherPort", mock(AvailabilitySearchEventPublisherPort.class));
            ctx.getBeanFactory().registerSingleton(
                    "availabilitySearchPersistencePort", mock(AvailabilitySearchPersistencePort.class));
            ctx.getBeanFactory().registerSingleton(
                    "availabilitySearchQueryPort", mock(AvailabilitySearchQueryPort.class));
            ctx.register(UseCaseConfiguration.class);
            ctx.refresh();

            assertAll(
                    () -> assertNotNull(ctx.getBean(PublishAvailabilitySearchUseCase.class)),
                    () -> assertNotNull(ctx.getBean(RegisterAvailabilitySearchUseCase.class)),
                    () -> assertNotNull(ctx.getBean(CountEqualSearchesUseCase.class)));
        }
    }
}
