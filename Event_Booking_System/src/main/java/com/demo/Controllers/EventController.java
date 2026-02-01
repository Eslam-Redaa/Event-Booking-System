package com.demo.Controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.demo.IO.EventRequest;
import com.demo.IO.MyResponse;
import com.demo.Services.EventServices;

@RestController
@RequestMapping("/events")
public class EventController {
	
	@Autowired
	EventServices eserv;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public MyResponse CreateEvent(@ModelAttribute ("request") EventRequest request ,
								  @RequestPart("image") MultipartFile image) throws IOException {
		
		return eserv.addEvent(request , image);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public MyResponse DeleteEvent(@PathVariable String id) throws IOException{
		return eserv.deleteEvent(id);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findByAdmin/{id}")
	public MyResponse GetEventByidPlusBookingsList(@PathVariable String id) {
		return eserv.getEventByIdPlusBookingsList(id);
	}
	
	
	@GetMapping("/find/{id}")
	public MyResponse GetEventByid(@PathVariable String id) {
		return eserv.getEventById(id);
	}
	
	
	@GetMapping("/all")
	public MyResponse GetAllEvents() {
		return eserv.getAllEvents();
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public MyResponse UpdateEvent(@PathVariable String id , @RequestBody EventRequest request) {
		return eserv.updateEvent(id, request);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/changeTotalSeats/{id}/{nseats}")
	public MyResponse ChangeEventTotalSeats
						( @PathVariable("id") String id , @PathVariable("nseats") int numOfSeats ) {
		return eserv.changeNumOfTotalSeats(id, numOfSeats);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/price/{id}/{np}")
	public MyResponse ChangePrice
					(@PathVariable("np") double price , @PathVariable("id") String id) {
		return eserv.changePrice(id, price);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/status/{id}/{nst}")
	public MyResponse ChangeEventStatus
					(@PathVariable("nst") String status , @PathVariable("id") String id) {
		return eserv.changeEventStatus(id, status);
	}
	
	
	@GetMapping("/allByStatus/{st}")
	public MyResponse GetAllEventsByStatus(@PathVariable String status) {
		return eserv.getEventsByStatus(status);
	}
	

}
