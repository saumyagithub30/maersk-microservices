package com.maersk.availabiltyservice.service;


import com.maersk.availabiltyservice.exception.InvalidContainerSizeException;
import com.maersk.availabiltyservice.model.AvailabilityRequest;
import com.maersk.availabiltyservice.model.InventoryResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final WebClient webClient;

    public boolean isInStockCheck(AvailabilityRequest availabilityRequest) {
        InventoryResponse inventoryResponse = webClient.get().uri("https://www.maersk.com/api/bookings/checkAvailable")
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .block();

        if (inventoryResponse.getAvailableSpace()>0) {
            return true;
        }
        return false;
    }

    public void validateInput(AvailabilityRequest availabilityRequest) throws InvalidContainerSizeException {
        int containerSize = availabilityRequest.getContainerSize();
        if(containerSize==20 || containerSize==40) {
            return;
        }
        throw new InvalidContainerSizeException("Please enter valid Container Size");
    }
}
