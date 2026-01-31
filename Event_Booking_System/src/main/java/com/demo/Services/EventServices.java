package com.demo.Services;

import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.demo.IO.EventRequest;
import com.demo.IO.MyResponse;


public interface EventServices {

	public MyResponse addEvent(EventRequest request ,MultipartFile image)throws IOException;//admin
	
	public MyResponse deleteEvent(String eventId) throws IOException;     //admin
	
	public MyResponse getEventByIdPlusBookingsList(String eventId);     //admin
	
	public MyResponse getEventById(String eventId);
	
	public MyResponse getAllEvents();    
	
	public MyResponse updateEvent(String eventId , EventRequest newEvent);     //admin
	
	public MyResponse changeNumOfTotalSeats(String eventId , int nSeats);      //admin
	
	public MyResponse riseAvailableSeats(String eventId , int numOfTickets);
	
	public MyResponse reduceAvailableSeats(String eventId , int numOfTickets);
	
	public MyResponse changePrice(String eventId , double price);     //admin
	
	public MyResponse changeEventStatus(String eventId , String status);     //admin
	
	public MyResponse getEventsByStatus(String status);
	
}
