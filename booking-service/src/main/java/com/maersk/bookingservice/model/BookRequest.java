package com.maersk.bookingservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequest {
    private String containerType;
    private int containerSize;
    private String origin;
    private String destination;
    private int quantity;
}
