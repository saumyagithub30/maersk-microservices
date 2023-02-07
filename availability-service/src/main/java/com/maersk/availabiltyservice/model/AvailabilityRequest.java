package com.maersk.availabiltyservice.model;

import com.maersk.availabiltyservice.utils.ContainerType;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class AvailabilityRequest {

    private ContainerType containerType;

    private int containerSize;

    @Size(min=5, max=20,message = "Please enter valid origin")
    private String origin;

    @Size(min=5, max=20,message = "Please enter valid destination")
    private String destination;

    @Min(1) @Max(100)
    private int quantity;


}
