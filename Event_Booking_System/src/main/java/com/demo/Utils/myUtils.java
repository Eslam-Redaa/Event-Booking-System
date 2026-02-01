package com.demo.Utils;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.demo.Entities.BookingEntity;
import com.demo.Entities.EventEntity;
import com.demo.Entities.UserEntity;
import com.demo.Exceptions.ResourceNotFoundException;
import com.demo.IO.BookingRequest;
import com.demo.IO.BookingResponse;
import com.demo.IO.EventRequest;
import com.demo.IO.EventResponse;
import com.demo.IO.UserRequest;
import com.demo.IO.UserResponse;
import com.demo.MyEnums.BookingStatus;
import com.demo.MyEnums.EventStatus;
import com.demo.Repositories.EventRepository;
import com.demo.Repositories.UserRepository;


@Service
public class myUtils {
	
	@Autowired
	EventRepository erepo;
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	PasswordEncoder myPasswordEncoder;

	public UserEntity convertUserToEntity(UserRequest request) {

		return UserEntity.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(myPasswordEncoder.encode(request.getPassword()))   
				.role( "ROLE_" + request.getRole().toUpperCase())
				.userId(UUID.randomUUID().toString())
				.bookingsList(new ArrayList<>())
				.build();
	}

	public UserResponse convertUserToResponse(UserEntity user , boolean BookingListCheck ) {

		return UserResponse.builder()
				.userId(user.getUserId())
				.name(user.getName())
				.email(user.getEmail())
				.role(user.getRole())
				.createdAt(user.getCreatedAt())
				.bookingsList( BookingListCheck ?  user.getBookingsList().stream()
						.map( ent -> convertBookingToResponsePlusUserAndEvent( false , true , ent) )
						.collect(Collectors.toList())	: new ArrayList<>()	)
				.build();
	}
	
	
	//****************************************************************************
		
	public BookingEntity convertBookingToEntity(BookingRequest request) {
		
		return BookingEntity.builder()
				.bookingId(UUID.randomUUID().toString())
				.numOfTickets(request.getNumOfTickets())
				.totalPrice( erepo.findByEventId(request.getEventId())
						.orElseThrow( () -> new ResourceNotFoundException("this event isn't found"))
						.getPrice() * request.getNumOfTickets() )
				.status(BookingStatus.WAITING.toString())
				.user(urepo.findByUserId(request.getUserId()).orElseThrow( () -> new ResourceNotFoundException("this User isn't found")) )
				.event(erepo.findByEventId(request.getEventId()).orElseThrow( () -> new ResourceNotFoundException("this event isn't found")) )
				.build();
	}
	
	public BookingResponse convertBookingToResponsePlusUserAndEvent(boolean userCheck , boolean eventCheck , BookingEntity booking)
	{
		return BookingResponse.builder()
				.bookingId(booking.getBookingId())
				.bookingDate(booking.getBookingDate().toString())
				.numOfTickets(booking.getNumOfTickets())
				.totalPrice(booking.getTotalPrice())
				.status(booking.getStatus())
				.user( userCheck ? convertUserToResponse(booking.getUser() , false) : null)
				.event( eventCheck ? convertEventToResponse(booking.getEvent() , false) : null )
				.build();			
	}
		
	
	//****************************************************************************
	

	public EventEntity convertEventToEntity(EventRequest request) {

		return EventEntity.builder()
					.eventId(UUID.randomUUID().toString())
					.title(request.getTitle())
					.description(request.getDescription())
					.location(request.getLocation())
					.eventDate(request.getEventDate())
					.totalSeats(request.getTotalSeats())
					.price(request.getPrice())
					.availableSeats(request.getTotalSeats())
					.status(EventStatus.ACTIVE.toString())
					.bookingsList(new ArrayList<>() )
					.build();
	}

	
	public EventResponse convertEventToResponse(EventEntity event  ,  boolean BookingListCheck) {
		
		return EventResponse.builder()
				.eventId(event.getEventId())
				.title(event.getTitle())
				.description(event.getDescription())
				.imageUrl(event.getImageUrl())
				.location(event.getLocation())
				.eventDate(event.getEventDate())
				.totalSeats(event.getTotalSeats())
				.price(event.getPrice())
				.availableSeats(event.getAvailableSeats())
				.status(event.getStatus()) 
				.bookingsList( BookingListCheck ?  event.getBookingsList().stream()
						.map( ent -> convertBookingToResponsePlusUserAndEvent( true , false , ent) )
						.collect(Collectors.toList())	: new ArrayList<>()	)
				.build();
	}
	

}
