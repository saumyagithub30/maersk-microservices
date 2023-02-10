package com.maersk.availablityservice.controller;

import com.maersk.availablityservice.exception.InvalidContainerSizeException;
import com.maersk.availablityservice.model.AvailabilityRequest;
import com.maersk.availablityservice.model.AvailabilityResponse;
import com.maersk.availablityservice.service.AvailabilityService;
import com.maersk.availablityservice.utils.ContainerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AvailabilityController.class)
public class AvailabilityControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    public void shouldCheckAvailability() throws InvalidContainerSizeException {
        AvailabilityRequest availabilityRequest = AvailabilityRequest.builder()
                .containerType(ContainerType.valueOf("DRY"))
                .containerSize(20)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        AvailabilityResponse availabilityResponse = AvailabilityResponse.builder().available(true).build();
        when(availabilityService.isInStockCheck(availabilityRequest)).thenReturn(Mono.just(availabilityResponse));
        webClient
                .post().uri("/api/bookings/checkAvailability")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(availabilityRequest), AvailabilityRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

    }
}
