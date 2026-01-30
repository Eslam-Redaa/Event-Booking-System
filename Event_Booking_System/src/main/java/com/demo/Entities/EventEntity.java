package com.demo.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String eventId;
	private String title;
	private String description;    
	private String location;        // how to make it as a real location 
	@Future(message = "the data must be in the future..")
	private LocalDate eventDate;
	private int totalSeats;
	private int availableSeats;
	private double price;
	private String status;
	
	private String imageUrl;
	private String imagePublicId;
	
	@OneToMany(mappedBy = "event" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	List<BookingEntity> bookingsList = new ArrayList<>();
	
}
