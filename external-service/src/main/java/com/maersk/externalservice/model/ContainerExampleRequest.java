package com.maersk.externalservice.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContainerExampleRequest {

    private int availableContainers;
}
