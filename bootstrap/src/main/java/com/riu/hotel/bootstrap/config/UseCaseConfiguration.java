package com.riu.hotel.bootstrap.config;

import com.riu.hotel.application.service.CountEqualSearchesService;
import com.riu.hotel.application.service.PublishAvailabilitySearchService;
import com.riu.hotel.application.service.RegisterAvailabilitySearchService;
import com.riu.hotel.port.in.CountEqualSearchesUseCase;
import com.riu.hotel.port.in.PublishAvailabilitySearchUseCase;
import com.riu.hotel.port.in.RegisterAvailabilitySearchUseCase;
import com.riu.hotel.port.out.AvailabilitySearchEventPublisherPort;
import com.riu.hotel.port.out.AvailabilitySearchPersistencePort;
import com.riu.hotel.port.out.AvailabilitySearchQueryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public PublishAvailabilitySearchUseCase publishAvailabilitySearchUseCase(
            AvailabilitySearchEventPublisherPort availabilitySearchEventPublisherPort
    ) {
        return new PublishAvailabilitySearchService(availabilitySearchEventPublisherPort);
    }

    @Bean
    public RegisterAvailabilitySearchUseCase registerAvailabilitySearchUseCase(
            AvailabilitySearchPersistencePort availabilitySearchPersistencePort
    ) {
        return new RegisterAvailabilitySearchService(availabilitySearchPersistencePort);
    }

    @Bean
    public CountEqualSearchesUseCase countEqualSearchesUseCase(
            AvailabilitySearchQueryPort availabilitySearchQueryPort
    ) {
        return new CountEqualSearchesService(availabilitySearchQueryPort);
    }
}
