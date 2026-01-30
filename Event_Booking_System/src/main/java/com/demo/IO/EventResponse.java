package com.demo.IO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

	private String eventId;
	private String title;
	private String description;
	private String imageUrl;
	private String location;
	private LocalDate eventDate;
	private int totalSeats;
	private int availableSeats;
	private double price;
	private String status;
	
	List<BookingResponse> bookingsList = new ArrayList<>();
	
}
