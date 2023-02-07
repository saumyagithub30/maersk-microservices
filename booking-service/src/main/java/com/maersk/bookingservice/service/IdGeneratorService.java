package com.maersk.bookingservice.service;

import com.maersk.bookingservice.model.IdGenerator;
import com.maersk.bookingservice.repository.IdGeneratorRepository;
import com.maersk.bookingservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
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

    /**
     * @return get and update the auto incr
     * as it is not directly supported in cassandra
     * we need to  handle it gracefully to avoid
     * and scalability race conditions
     * e.g. add the snippet in  synchronize block
     */
    public String generateBookingId() {

        Mono<IdGenerator> obj = idGeneratorRepository.findByClusterId(Constants.CLUSTER_ID);
        final IdGenerator[] item = new IdGenerator[1];
        obj.subscribe(idGenerator -> {
           item[0] = IdGenerator.builder()
                    .clusterId(idGenerator.getClusterId())
                    .id(idGenerator.getId())
                    .build();
        }, error -> {
            throw new RuntimeException("Internal Server Error");
        });
        String bookingId = String.format("%s00000%s", item[0].getClusterId(), item[0].getId());
        item[0].setId(item[0].getId()+1);
        idGeneratorRepository.save(item[0]);
        return bookingId;

    }


}
