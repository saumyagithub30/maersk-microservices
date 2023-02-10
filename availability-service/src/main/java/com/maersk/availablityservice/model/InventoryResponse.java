package com.maersk.availablityservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class InventoryResponse {
    @JsonProperty("availableSpace")
    private int availableSpace;
}
