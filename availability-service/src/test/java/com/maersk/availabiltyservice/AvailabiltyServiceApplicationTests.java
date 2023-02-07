package com.maersk.availabiltyservice;

import com.maersk.availabiltyservice.controller.AvailabilityController;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class AvailabiltyServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AvailabilityController availabilityController;

	@Test
	void contextLoads() {
		assertThat(availabilityController).isNotNull();
	}

	@Test
	public void whenPostRequestToCheckAvailability_thenCorrectResponse() throws Exception {
		String checkRequest = "{\"containerType\": \"DRY\", \"containerSize\" : \"18\", \"origin\": \"Southampton\", \"destination\": \"Singapore\",\"quantity\" : \"5\" }";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings/checkavailability")
						.content(checkRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.containerSize", Is.is("Please Enter Valid Container Size")))
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON));
	}

}
