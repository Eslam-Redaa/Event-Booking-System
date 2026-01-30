package com.demo.IO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingResponse {

	private String bookingId;
	private int numOfTickets;
	private double totalPrice;
	private String bookingDate;      
	private String status;
	
	private UserResponse user;
	private EventResponse event;
}
