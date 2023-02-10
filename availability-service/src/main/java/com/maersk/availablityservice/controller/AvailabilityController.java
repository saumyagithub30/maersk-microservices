package com.maersk.availablityservice.controller;

import com.maersk.availablityservice.exception.InvalidContainerSizeException;
import com.maersk.availablityservice.model.AvailabilityRequest;
import com.maersk.availablityservice.model.AvailabilityResponse;
import com.maersk.availablityservice.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping(value = "/checkAvailability", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<AvailabilityResponse> isInStock(@Valid @RequestBody AvailabilityRequest availabilityRequest) throws InvalidContainerSizeException {
        return availabilityService.isInStock(availabilityRequest);
    }

}
