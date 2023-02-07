package com.maersk.bookingservice.service;

import com.maersk.bookingservice.model.IdGenerator;
import com.maersk.bookingservice.repository.IdGeneratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;


@RequiredArgsConstructor
@Service
public class IdGeneratorService {

    @Autowired
    private final IdGeneratorRepository idGeneratorRepository;

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

    public String generateBookingId() {
//        idGeneratorRepository.findByClusterId();
        return "957000002";
    }
}
