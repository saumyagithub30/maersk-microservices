package com.maersk.availablityservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailabilityResponse {
    private Boolean available;
}
