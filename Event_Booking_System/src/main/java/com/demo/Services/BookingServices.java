package com.demo.Services;

import org.springframework.stereotype.Component;
import com.demo.IO.BookingRequest;
import com.demo.IO.MyResponse;

@Component
public interface BookingServices {
	
	public MyResponse makeBooking(BookingRequest request);
	
	public MyResponse deleteBooking(String bookingId);    //admin
	
	public MyResponse cancelBooking(String bookingId);
	
	public MyResponse getAllBookings();     //admin
	
	public MyResponse getBookingById(String id);     //admin
	

}
