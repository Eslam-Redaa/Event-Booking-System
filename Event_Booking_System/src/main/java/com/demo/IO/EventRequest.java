package com.demo.IO;

import java.time.LocalDate;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

	private String title;
	private String description;
	private String location;        
	@Future
	private LocalDate eventDate;
	private int totalSeats;
	private double price;
	
}
