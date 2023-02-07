package com.maersk.bookingservice.model;

import lombok.Data;

@Data
public class BookRequest {
    private String containerType;
    private int containerSize;
    private String origin;
    private String destination;
    private int quantity;
}
