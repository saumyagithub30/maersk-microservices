package com.maersk.bookingservice.service;

import com.maersk.bookingservice.exception.InvalidBookingRequest;
import com.maersk.bookingservice.model.BookRequest;
import com.maersk.bookingservice.model.BookResponse;
import com.maersk.bookingservice.model.Booking;
import com.maersk.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private IdGeneratorService idGeneratorService;

    @Mock
    private BookingRepository bookingRepository;

    private final String TEST_ID = "957000001";

    @Test
    public void shouldGetBooking() throws InvalidBookingRequest {
        BookRequest bookrequest = BookRequest.builder()
                .containerType("DRY")
                .containerSize(20)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        Booking booking = Booking.builder()
                .id("957000001")
                .containerType("DRY")
                .containerSize(20)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        when(bookingRepository.save(booking)).thenReturn(Mono.just(booking));
        when(idGeneratorService.generateBookingId()).thenReturn("1");
        Mono<BookResponse> bookResponseMono = bookingService.createBooking(bookrequest);
        StepVerifier
                .create(bookResponseMono)
                .consumeNextWith(bookResponse -> {
                    assertEquals(bookResponse.getBookingRef(), TEST_ID);
                })
                .verifyComplete();
    }

    @Test
    public void shouldGetInvalidContainerSize_ExceptionThrown() throws InvalidBookingRequest {
        BookRequest bookrequest = BookRequest.builder()
                .containerType("DRY")
                .containerSize(10)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        when(bookingRepository.findById(TEST_ID)).thenReturn(Mono.empty());
        Mono<BookResponse> bookResponseMono = bookingService.createBooking(bookrequest);
        StepVerifier
                .create(bookResponseMono)
                .expectErrorMatches(throwable -> throwable instanceof InvalidBookingRequest)
                .verify();
    }
}
