package com.maersk.bookingservice.controller;

import com.maersk.bookingservice.exception.InvalidBookingRequest;
import com.maersk.bookingservice.model.BookRequest;
import com.maersk.bookingservice.model.BookResponse;

import com.maersk.bookingservice.model.Booking;
import com.maersk.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping(value = "/v1/containers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookResponse> save(@RequestBody BookRequest bookRequest) throws InvalidBookingRequest {
        return bookingService.createBooking(bookRequest);
    }
}
