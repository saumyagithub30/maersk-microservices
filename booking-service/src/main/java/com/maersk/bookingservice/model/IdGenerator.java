package com.maersk.bookingservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("id_generator")
@Builder
@Data
public class IdGenerator {
    @PrimaryKey
    private int id;
    @Column("cluster_id")
    private int clusterId;
}
