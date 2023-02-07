package com.maersk.availabiltyservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailabilityResponse {
    private boolean available;
}
