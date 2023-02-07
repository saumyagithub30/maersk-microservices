package com.maersk.availabiltyservice.service;


import com.maersk.availabiltyservice.exception.InvalidContainerSizeException;
import com.maersk.availabiltyservice.model.AvailabilityRequest;
import com.maersk.availabiltyservice.model.AvailabilityResponse;
import com.maersk.availabiltyservice.model.InventoryResponse;
import com.maersk.availabiltyservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final WebClient webClient;

    public AvailabilityResponse isInStockCheck(AvailabilityRequest availabilityRequest) throws InvalidContainerSizeException {
        boolean isAvailable = false;
        if(validateInput(availabilityRequest)) {
            try{
                InventoryResponse inventoryResponse = webClient.get().uri(Constants.MAERSK_EXTERNAL_URL)
                        .retrieve()
                        .bodyToMono(InventoryResponse.class)
                        .block();

                isAvailable = inventoryResponse.getAvailableSpace()>0 ? true: false;
            } catch(Exception e) {

            }

        } else {
            throw new InvalidContainerSizeException("Please enter valid Container Size");
        }

        AvailabilityResponse availabilityResponse = AvailabilityResponse.builder()
                .available(isAvailable).build();
        return availabilityResponse;
    }

    public boolean validateInput(AvailabilityRequest availabilityRequest) {
        int containerSize = availabilityRequest.getContainerSize();
        if(containerSize==20 || containerSize==40) {
            return true;
        }
        return false;
    }
}
