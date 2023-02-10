package com.maersk.externalservice.service;

import com.maersk.externalservice.model.ExternalResponse;
import com.maersk.externalservice.model.ContainerExampleRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExternalService {
    public Mono<ExternalResponse> checkAvailableContainers() {
        ContainerExampleRequest exampleRequest = ContainerExampleRequest.builder()
                .availableContainers(6)
                .build();

        return Mono.just(exampleRequest)
                .map(request -> {
                    ExternalResponse externalResponse = ExternalResponse.builder()
                            .availableSpace(request.getAvailableContainers()).build();
                    return externalResponse;
                });
    }
}
