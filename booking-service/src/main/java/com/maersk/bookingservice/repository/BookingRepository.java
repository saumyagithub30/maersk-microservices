package com.maersk.bookingservice.repository;

import com.maersk.bookingservice.model.Booking;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, String> {
    @Query("CREATE TABLE IF NOT EXISTS bookings (id text PRIMARY KEY, container_type text, container_size int, origin text, destination text, quantity int, timestamp timestamp);")
    Mono<Void> createTable();
}