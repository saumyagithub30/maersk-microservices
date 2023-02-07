package com.maersk.bookingservice.controller;

import com.maersk.bookingservice.exception.InvalidBookingRequest;
import com.maersk.bookingservice.model.BookRequest;
import com.maersk.bookingservice.model.BookResponse;

import com.maersk.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/v1/containers")
    public BookResponse save(@RequestBody BookRequest bookRequest) throws InvalidBookingRequest {
        BookResponse bookResponse = bookingService.createBooking(bookRequest);
        return bookResponse;
    }
}
