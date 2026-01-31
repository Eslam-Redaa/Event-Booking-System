package com.demo.Services;

import org.springframework.stereotype.Component;
import com.demo.IO.LoginRequest;
import com.demo.IO.UserRequest;
import com.demo.IO.MyResponse;

@Component
public interface UserServices {
	
	public MyResponse addUser (UserRequest user); 
	
	public MyResponse login(LoginRequest request);  // 

	public MyResponse getAllUsers();        //admin
	
	public MyResponse deleteUser(String userId);    //admin
	
	public MyResponse updateUser(String userId , UserRequest nUser);
	
	public MyResponse findUserById(String userId);    //admin
	
	public MyResponse getUserBookings(String userId);
	
	public MyResponse getUserByEmail(String email);   //admin
	
	public String getUserRole(String email);
}
