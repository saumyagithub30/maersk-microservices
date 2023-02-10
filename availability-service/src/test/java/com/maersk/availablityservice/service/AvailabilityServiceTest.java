package com.maersk.availablityservice.service;

import com.maersk.availablityservice.exception.InvalidContainerSizeException;
import com.maersk.availablityservice.model.AvailabilityRequest;
import com.maersk.availablityservice.model.AvailabilityResponse;
import com.maersk.availablityservice.utils.ContainerType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class AvailabilityServiceTest {

    @InjectMocks
    private AvailabilityService availabilityService;

    @Test
    public void shouldCheckAvailability_ExceptionThrown() throws InvalidContainerSizeException {
        AvailabilityRequest availabilityRequest = AvailabilityRequest
                .builder()
                .containerType(ContainerType.valueOf("DRY"))
                .containerSize(10)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        Mono<AvailabilityResponse> availabilityResponseMono = availabilityService.isInStock(availabilityRequest);
        StepVerifier
                .create(availabilityResponseMono)
                .expectErrorMatches(throwable -> throwable instanceof InvalidContainerSizeException)
                .verify();
    }


}
