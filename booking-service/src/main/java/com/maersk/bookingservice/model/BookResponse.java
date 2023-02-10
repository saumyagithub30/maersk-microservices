package com.maersk.bookingservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private String bookingRef;
}
