package com.maersk.bookingservice;

import com.maersk.bookingservice.model.Booking;
import com.maersk.bookingservice.repository.BookingRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Instant;


@SpringBootTest
@AutoConfigureMockMvc
class BookingServiceApplicationTests {

	private BookingRepository bookingRepository;
	@PostConstruct
	public void initialize() {
		Booking obj = Booking.builder().id("957000001")
				.containerType("DRY")
				.containerSize(20)
				.origin("Southampton")
				.destination("Singapore")
				.quantity(5)
				.createdDate(Timestamp.from(Instant.now())).build();

		bookingRepository.createTable()
				.then(bookingRepository.findById("1")
						.flatMap(booking -> Mono.empty())
						.switchIfEmpty(bookingRepository.save(obj)));
	}




}
