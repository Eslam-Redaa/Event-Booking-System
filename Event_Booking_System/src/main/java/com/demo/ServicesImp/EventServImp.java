package com.demo.ServicesImp;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.demo.Entities.EventEntity;
import com.demo.Exceptions.ResourceNotFoundException;
import com.demo.IO.EventRequest;
import com.demo.IO.MyResponse;
import com.demo.MyEnums.EventStatus;
import com.demo.Repositories.EventRepository;
import com.demo.Services.EventServices;
import com.demo.Utils.myUtils;

@Service
public class EventServImp implements EventServices{
	
	@Autowired
	myUtils utils;
	
	@Autowired
	EventRepository erepo;
	
	@Autowired
	Cloudinary cloudinary;

	@Override
	public MyResponse addEvent(EventRequest request , MultipartFile image) throws IOException {
		
		Map uploadRequest = cloudinary.uploader().upload(image.getBytes() ,
					ObjectUtils.asMap("upload_preset","Event-System") );
		
		EventEntity event = utils.convertEventToEntity(request);
		event.setImageUrl(uploadRequest.get("secure_url").toString());
		event.setImagePublicId(uploadRequest.get("public_id").toString() );
		event = erepo.save(event);
		
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse deleteEvent(String eventId) throws IOException {

		if( !erepo.existsByEventId(eventId) )
		{
			throw new RuntimeException("this event is not found");
		}
		EventEntity event = erepo.findByEventId(eventId).get();
		cloudinary.uploader().destroy(event.getImagePublicId(), ObjectUtils.emptyMap());
		erepo.delete(event);		
		
		MyResponse response = new MyResponse();
		response.setSuccessMessage("the event deleted successfully");
		response.setStatusCode(200);
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse getEventByIdPlusBookingsList(String eventId) {

		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , true));
		response.setStatusCode(200);
		return response;
	}
	
	//**********************************************************************
	
	@Override
	public MyResponse getEventById(String eventId) {

		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse getAllEvents() {

		MyResponse response = new MyResponse();
		response.setEventsList( erepo.findAll().stream()
				.map( ent -> utils.convertEventToResponse(ent , false) ).collect(Collectors.toList()) );
		response.setStatusCode(200);
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse updateEvent(String eventId, EventRequest newEvent) {

		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		event.setTitle(newEvent.getTitle());
		event.setDescription(newEvent.getDescription());
		event.setLocation(newEvent.getLocation());
		event.setEventDate(newEvent.getEventDate());
		event.setPrice(newEvent.getPrice());
		event.setTotalSeats(newEvent.getTotalSeats());
		
		erepo.save(event);
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , true));
		response.setStatusCode(200);
		response.setSuccessMessage("Event updated Successfully..");
		return response;
	}

	//**********************************************************************

	@Override
	public MyResponse changeEventStatus(String eventId , String status) {

		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		if(status.equals("ACTIVE"))
		{
			event.setStatus(EventStatus.ACTIVE.toString());
		}
		else if(status.equals("SOLD_OUT"))
		{
			event.setStatus(EventStatus.SOLD_OUT.toString());
		}
		else if(status.equals("CANCELLD"))
		{
			event.setStatus(EventStatus.CANCELLD.toString());
		}
		else
		{
			throw new RuntimeException("Enter the correct name of the Status..");
		}
		
		erepo.save(event);
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		response.setSuccessMessage("Event updated Successfully..");
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse getEventsByStatus(String status) {
		
		MyResponse response = new MyResponse();
		response.setEventsList( erepo.findByEventStatus(status).stream()
				.map( ent -> utils.convertEventToResponse(ent , false) ).collect(Collectors.toList()) );
		response.setStatusCode(200);
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse changeNumOfTotalSeats(String eventId, int nSeats) {
		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		event.setTotalSeats(nSeats);
		erepo.save(event);
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		response.setSuccessMessage("Event updated Successfully..");
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse changePrice(String eventId, double price) {

		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		event.setPrice(price);
		erepo.save(event);
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		response.setSuccessMessage("Event updated Successfully..");
		return response;
	}
	
	
	//**********************************************************************

	@Override
	public MyResponse riseAvailableSeats(String eventId, int numOfTickets) {

		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		event.setAvailableSeats( event.getAvailableSeats() + numOfTickets );
		erepo.save(event);
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		response.setSuccessMessage("Event updated Successfully..");
		return response;
	}
	
	//**********************************************************************

	@Override
	public MyResponse reduceAvailableSeats(String eventId, int numOfTickets) {
		
		EventEntity event = erepo.findByEventId(eventId)
				.orElseThrow( () -> new ResourceNotFoundException("this event is not found" ));
		
		event.setAvailableSeats( event.getAvailableSeats() - numOfTickets );
		erepo.save(event);
		MyResponse response = new MyResponse();
		response.setEvent( utils.convertEventToResponse(event , false));
		response.setStatusCode(200);
		response.setSuccessMessage("Event updated Successfully..");
		return response;
	}

}
