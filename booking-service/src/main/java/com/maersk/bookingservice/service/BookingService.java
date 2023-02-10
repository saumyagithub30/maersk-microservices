package com.maersk.bookingservice.service;

import com.maersk.bookingservice.exception.InvalidBookingRequest;
import com.maersk.bookingservice.exception.InvalidContainerSizeException;
import com.maersk.bookingservice.model.BookRequest;
import com.maersk.bookingservice.model.BookResponse;
import com.maersk.bookingservice.model.Booking;
import com.maersk.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final IdGeneratorService idGeneratorService;

    @PostConstruct
    public void initialize() {
        Booking obj = Booking.builder().id("957000001")
                .containerType("DRY")
                .containerSize(20)
                .origin("Southampton")
                .destination("Singapore")
                .quantity(5)
                .createdDate(Timestamp.from(Instant.now())).build();
                bookingRepository.createTable()
                .then(bookingRepository.findById("1")
                        .flatMap(booking -> Mono.empty())
                        .switchIfEmpty(bookingRepository.save(obj)));
    }
    public Mono<BookResponse> createBooking(BookRequest bookRequest) throws InvalidBookingRequest {
        try {
            validateBookingRequest(bookRequest);
        } catch (InvalidContainerSizeException e) {
            throw new InvalidBookingRequest("Invalid Booking Request");
        }

        Booking booking = builder(bookRequest);
        Mono<Booking> item = bookingRepository.save(booking);
        String id = item.toProcessor().block().getId();
        return Mono.just(BookResponse.builder().bookingRef(id).build());
    }

    private void validateBookingRequest(BookRequest bookRequest) throws InvalidContainerSizeException {
        int containerSize = bookRequest.getContainerSize();
        if(containerSize==20 || containerSize==40) {
            return;
        }
        throw new InvalidContainerSizeException("Please enter valid Container Size");
    }

    private Booking builder(BookRequest bookRequest) {
        String id = idGeneratorService.generateBookingId();
        return Booking.builder()
                .id(id)
                .containerSize(bookRequest.getContainerSize())
                .containerType(bookRequest.getContainerType())
                .origin(bookRequest.getOrigin())
                .destination(bookRequest.getDestination())
                .quantity(bookRequest.getQuantity())
                .createdDate(Timestamp.from(Instant.now()))
                .build();
    }
}
