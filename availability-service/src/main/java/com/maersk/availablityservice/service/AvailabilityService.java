package com.maersk.availablityservice.service;


import com.maersk.availablityservice.exception.InvalidContainerSizeException;
import com.maersk.availablityservice.model.AvailabilityRequest;
import com.maersk.availablityservice.model.AvailabilityResponse;
import com.maersk.availablityservice.model.InventoryResponse;
import com.maersk.availablityservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final WebClient webClient;

    public Mono<AvailabilityResponse> isInStockCheck(AvailabilityRequest availabilityRequest) throws InvalidContainerSizeException {
        Mono<Boolean> isAvailable = null;
        final Mono<AvailabilityResponse>[] item = new Mono[]{Mono.just(AvailabilityResponse.builder().build())};
        if(validateInput(availabilityRequest)) {
             return webClient.get().uri(Constants.MAERSK_EXTERNAL_URL)
                    .retrieve()
                    .bodyToMono(InventoryResponse.class)
                    .map(storageData -> storageData.getAvailableSpace() > 0)
                     .flatMap(space -> {
                         return Mono.just(AvailabilityResponse.builder().available(space).build());
                     });
        } else {
            throw new InvalidContainerSizeException("Please enter valid Container Size");
        }
    }

    public boolean validateInput(AvailabilityRequest availabilityRequest) {
        int containerSize = availabilityRequest.getContainerSize();
        if(containerSize==20 || containerSize==40) {
            return true;
        }
        return false;
    }
}
