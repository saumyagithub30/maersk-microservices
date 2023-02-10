package com.maersk.externalservice.controller;

import com.maersk.externalservice.model.ExternalResponse;
import com.maersk.externalservice.service.ExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalService externalService;

    @GetMapping(value = "/checkAvailable")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ExternalResponse> isContainerAvailable() {
        return externalService.checkAvailableContainers();
    }
}
