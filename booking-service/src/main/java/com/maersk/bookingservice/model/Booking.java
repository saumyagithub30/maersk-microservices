package com.maersk.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("bookings")
@Data
@Builder
public class Booking {
    @PrimaryKey
    private String id;
    @Column("container_type")
    private String containerType;
    @Column("container_size")
    private int containerSize;
    @Column("origin")
    private String origin;
    @Column("destination")
    private String destination;
    @Column("quantity")
    private int quantity;
    @Column("createdDate")
    private Timestamp createdDate;

}
