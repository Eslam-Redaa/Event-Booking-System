package com.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.IO.LoginRequest;
import com.demo.IO.UserRequest;
import com.demo.IO.MyResponse;
import com.demo.Services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserServices userv;

	@PostMapping("/login")
	public MyResponse Login(@RequestBody LoginRequest request) {
		return userv.login(request);
	}
	
	
	@PostMapping("/add")
	public MyResponse createUser(@RequestBody UserRequest request) {
		return userv.addUser(request);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all")
	public MyResponse GetAllUsers() {
		return userv.getAllUsers();
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public MyResponse DeleteUser(@PathVariable String id) {
		return userv.deleteUser(id);
	}
	
	
	@PutMapping("/update/{id}")
	public MyResponse UpdateUser(@PathVariable String id , UserRequest request) {
		return userv.updateUser(id, request);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/find/{id}")
	public MyResponse GetUserById(@PathVariable String id) {
		return userv.findUserById(id);
	}
	
	
	@GetMapping("/userBookList/{id}")
	public MyResponse GetUserBookingsList(@PathVariable String id) {
		return userv.getUserBookings(id);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findByEmail/{email}")
	public MyResponse GetUserByEmail(@PathVariable String email) {
		return userv.getUserByEmail(email);
	}

}
