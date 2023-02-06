package com.maersk.availabiltyservice.controller;

import com.maersk.availabiltyservice.exception.FinalException;
import com.maersk.availabiltyservice.exception.InvalidContainerSizeException;
import com.maersk.availabiltyservice.model.AvailabilityRequest;
import com.maersk.availabiltyservice.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/checkavailability")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestBody AvailabilityRequest availabilityRequest) throws FinalException {
        try {
            availabilityService.validateInput(availabilityRequest);
        } catch(InvalidContainerSizeException e1) {
            throw new FinalException(1, "Please Enter Valid Container Size");
        }

        return availabilityService.isInStockCheck(availabilityRequest);
    }

}
