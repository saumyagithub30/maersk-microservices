package com.maersk.bookingservice.repository;

import com.maersk.bookingservice.model.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataCassandraTest
@ExtendWith(SpringExtension.class)
public class BookingRepositoryTests {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void shouldSaveSingleBookingRequest() {
        Booking bookrequest = Booking.builder()
                .id("957000001")
                .containerType("DRY")
                .containerSize(20)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        Publisher<Booking> setup = bookingRepository.deleteAll().thenMany(bookingRepository.save(bookrequest));
        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void shouldSaveUser() {
        String TEST_ID = "957000001";
        Booking bookrequest = Booking.builder()
                .id("957000001")
                .containerType("DRY")
                .containerSize(20)
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();
        Publisher<Booking> setup = bookingRepository.deleteAll().then(bookingRepository.save(bookrequest));
        Mono<Booking> find = bookingRepository.findById(TEST_ID);
        Publisher<Booking> composite = Mono
                .from(setup)
                .then(find);
        StepVerifier
                .create(composite)
                .consumeNextWith(request -> {
                    assertNotNull(request.getId());
                    assertEquals(request.getId(), TEST_ID);
                    assertEquals(request.getOrigin(), "Southampton");
                    assertEquals(request.getDestination(), "Singapore");
                })
                .verifyComplete();
    }
}
