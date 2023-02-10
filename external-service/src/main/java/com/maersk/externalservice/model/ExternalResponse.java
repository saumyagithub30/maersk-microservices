package com.maersk.externalservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalResponse {
    private int availableSpace;
}
