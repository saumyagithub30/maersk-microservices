package com.maersk.bookingservice.controller;

import com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders;
import com.maersk.bookingservice.exception.InvalidBookingRequest;
import com.maersk.bookingservice.model.BookRequest;
import com.maersk.bookingservice.model.BookResponse;
import com.maersk.bookingservice.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookingController.class)
public class BookingControllerTest {

    private final String TEST_ID = "957000001";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookingService bookingService;

    @Test
    public void shouldCreateBooking() throws InvalidBookingRequest {
        BookRequest bookrequest = BookRequest.builder()
                .containerType("DRY")
                .containerSize(20)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        BookResponse bookResponse = BookResponse.builder().bookingRef("957000001").build();
        when(bookingService.createBooking(bookrequest)).thenReturn(Mono.just(bookResponse));
        webClient
                .post().uri("/api/bookings/v1/containers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bookrequest), BookRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

}
