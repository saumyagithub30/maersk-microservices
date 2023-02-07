package com.maersk.availabiltyservice.controller;

import com.maersk.availabiltyservice.exception.InvalidContainerSizeException;
import com.maersk.availabiltyservice.model.AvailabilityRequest;
import com.maersk.availabiltyservice.model.AvailabilityResponse;
import com.maersk.availabiltyservice.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping(value = "/checkavailability", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AvailabilityResponse> isInStock(@Valid @RequestBody AvailabilityRequest availabilityRequest) throws InvalidContainerSizeException {
        AvailabilityResponse availabilityResponse = availabilityService.isInStockCheck(availabilityRequest);
        return ResponseEntity.ok(availabilityResponse);
    }

}
