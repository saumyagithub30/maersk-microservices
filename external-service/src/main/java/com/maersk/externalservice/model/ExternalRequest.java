package com.maersk.externalservice.model;

import lombok.Data;

@Data
public class ExternalRequest {

    private String containerType;

    private int containerSize;

    private String origin;

    private String destination;

    private int quantity;
}
