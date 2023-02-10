package com.maersk.bookingservice;

import com.maersk.bookingservice.model.IdGenerator;
import com.maersk.bookingservice.repository.IdGeneratorRepository;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

public class IdGeneratorServiceTests {

    private IdGeneratorRepository idGeneratorRepository;

    @PostConstruct
    public void initialize() {
        IdGenerator obj = IdGenerator.builder().id(1)
                .clusterId(957)
                .build();
        idGeneratorRepository.createTable()
                .then(idGeneratorRepository.findById(1)
                        .flatMap(booking -> Mono.empty())
                        .switchIfEmpty(idGeneratorRepository.save(obj)));
    }
}
