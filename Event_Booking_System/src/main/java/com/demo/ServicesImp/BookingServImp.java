package com.demo.ServicesImp;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.Entities.BookingEntity;
import com.demo.Exceptions.ResourceNotFoundException;
import com.demo.IO.BookingRequest;
import com.demo.IO.MyResponse;
import com.demo.MyEnums.BookingStatus;
import com.demo.Repositories.BookingRepository;
import com.demo.Repositories.EventRepository;
import com.demo.Repositories.UserRepository;
import com.demo.Services.BookingServices;
import com.demo.Services.EventServices;
import com.demo.Services.UserServices;
import com.demo.Utils.myUtils;

@Service
public class BookingServImp implements BookingServices{
	
	@Autowired
	myUtils utils;
	
	@Autowired
	BookingRepository brepo;
	
	@Autowired
	EventRepository erepo;
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	EventServices eserv;
	
	@Autowired
	UserServices userv;

	@Override
	public MyResponse makeBooking(BookingRequest request) {

		if( !urepo.existsByUserId(request.getUserId()) || 
			!erepo.existsByEventId(request.getEventId()) ||
			!eserv.getEventById(request.getEventId()).getEvent().getStatus().equals("ACTIVE") ||
			eserv.getEventById(request.getEventId()).getEvent().getAvailableSeats() <= 0  )
		{
			throw new RuntimeException("we can't book this Event , maybe it's CANCELLED or SOLD_OUT");
		}
		
		if( request.getNumOfTickets() >  eserv.getEventById(request.getEventId()).getEvent().getAvailableSeats() )
		{
			throw new RuntimeException("we can't book this Event , there is no enough Seats");
		}
				
		
		BookingEntity booking = utils.convertBookingToEntity(request);
		booking = brepo.save(booking);
		
		eserv.reduceAvailableSeats( request.getEventId() , booking.getNumOfTickets());
		
		if( erepo.findByEventId(request.getEventId()).get().getAvailableSeats() == 0 ) {
			eserv.changeEventStatus(request.getEventId() , "SOLD_OUT");
		}
		
		MyResponse response = new MyResponse();
		response.setBooking( utils.convertBookingToResponsePlusUserAndEvent(true , true ,booking) );
		response.setSuccessMessage("booking has made Successfully..");
		response.setStatusCode(200);
		
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse deleteBooking(String bookingId) {
		
		BookingEntity booking = brepo.findByBookingId(bookingId)
				.orElseThrow( () -> new ResourceNotFoundException("this booking not found"));
		
		if( !booking.getStatus().toString().equals("CANCELLED"))
		{
			throw new RuntimeException("Cancel the Booking first then Delete it.. ");
		}
		
		brepo.delete(booking);
		MyResponse response = new MyResponse();
		response.setSuccessMessage("booking deleted Successfully..");
		response.setStatusCode(200);
		
		return response;
	}
	
	//**********************************************************************
	
	@Override
	public MyResponse cancelBooking(String bookingId) {
		
		BookingEntity booking = brepo.findByBookingId(bookingId)
				.orElseThrow( () -> new ResourceNotFoundException("this booking not found"));
		
		booking.setStatus(BookingStatus.CANCELLED.toString());
		eserv.riseAvailableSeats(booking.getEvent().getEventId() , booking.getNumOfTickets());
		
		brepo.save(booking);
		MyResponse response = new MyResponse();
		response.setSuccessMessage("booking Cancelled Successfully..");
		response.setStatusCode(200);
		
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse getAllBookings() {

		MyResponse response = new MyResponse();
		response.setBookingsList( brepo.findAll().stream()
				.map( ent -> utils.convertBookingToResponsePlusUserAndEvent(true , true , ent)).collect(Collectors.toList()) );
		response.setStatusCode(200);
		
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse getBookingById(String id) {

		BookingEntity booking = brepo.findByBookingId(id)
				.orElseThrow( () -> new ResourceNotFoundException("this booking not found"));
		
		MyResponse response = new MyResponse();
		response.setBooking(utils.convertBookingToResponsePlusUserAndEvent(true, true, booking));
		response.setStatusCode(200);
		
		return response;
	}	
	

}
