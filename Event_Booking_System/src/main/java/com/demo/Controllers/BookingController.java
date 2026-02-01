package com.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.IO.BookingRequest;
import com.demo.IO.MyResponse;
import com.demo.Services.BookingServices;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	@Autowired
	BookingServices bserv;

	@PostMapping("/book")
	public MyResponse BookEvent(@RequestBody BookingRequest request) {
		return bserv.makeBooking(request);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public MyResponse DeleteBook(@PathVariable String id) {
		return bserv.deleteBooking(id);
	}
	
	
	@GetMapping("/cancel/{id}")
	public MyResponse CancelBook(@PathVariable String id) {
		return bserv.cancelBooking(id);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public MyResponse GetAllBookings() {
		return bserv.getAllBookings();
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/find/{id}")
	public MyResponse FindBook(@PathVariable String id) {
		return bserv.getBookingById(id);
	}

	
}
