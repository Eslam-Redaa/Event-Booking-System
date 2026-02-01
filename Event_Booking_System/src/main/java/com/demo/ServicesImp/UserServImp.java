package com.demo.ServicesImp;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.demo.Entities.UserEntity;
import com.demo.Exceptions.ResourceNotFoundException;
import com.demo.IO.LoginRequest;
import com.demo.IO.UserRequest;
import com.demo.IO.MyResponse;
import com.demo.Repositories.UserRepository;
import com.demo.Services.UserServices;
import com.demo.Utils.JwtUtils;
import com.demo.Utils.myUtils;

@Service
public class UserServImp implements UserServices{

	@Autowired
	UserRepository urepo;
	
	@Autowired
	myUtils utils;
	
	@Autowired
	JwtUtils jutils;
	
	@Autowired
	AuthenticationManager authmanager;
	
	@Override
	public MyResponse addUser(UserRequest request) {
		
		if(urepo.existsByEmail(request.getEmail()))
		{
			throw new RuntimeException("this email is already Exsist  ->  "+ request.getEmail() +  "  , user another one !");
		}
		
		if(request.getRole() == null ) {
			request.setRole("user");
		}
		
		UserEntity user = utils.convertUserToEntity(request);
		user = urepo.save(user);
		
		MyResponse response = new MyResponse();
		response.setUser( utils.convertUserToResponse(user , false) );
		response.setStatusCode(200);
		
		return response;
	}
	
//----------------------------------------------------------------
	
	@Override
	public MyResponse login(LoginRequest request) {    

		Authentication authentication = authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
		if(!authentication.isAuthenticated())
		{
			throw new RuntimeException("Wrong Email or Password ......");
		}
		
		String token = jutils.generateToken(request.getEmail());
		String role = getUserRole(request.getEmail());
		
		MyResponse response = new MyResponse();
		response.setToken(token);
		response.setRole(role);
		response.setExpirationDate(jutils.extractExpirationDate(token).toString() );
		response.setStatusCode(200);
		response.setSuccessMessage("Authenticated Successfully..");
		
		return response;
	}
	
	//----------------------------------------------------------------

	@Override
	public MyResponse getAllUsers() {

		MyResponse response = new MyResponse();
		response.setUsersList(urepo.findAll().stream()
					.map( ent -> utils.convertUserToResponse(ent , false)).collect(Collectors.toList()) );
		response.setStatusCode(200);
		
		return response;
	}
	
	//----------------------------------------------------------------

	@Override
	public MyResponse deleteUser(String userId) {

		UserEntity user = urepo.findByUserId(userId)
				.orElseThrow( () -> new ResourceNotFoundException("user not found  :  " + userId));
		
		urepo.delete(user);
		
		MyResponse response = new MyResponse();
		response.setErrorMessage("user deleted Successfully..");
		response.setStatusCode(200);
		
		return response;
	}
	
	//----------------------------------------------------------------

	@Override
	public MyResponse findUserById(String userId) {

		UserEntity user = urepo.findByUserId(userId)
				.orElseThrow( () -> new ResourceNotFoundException("user not found  :  " + userId));
		
		MyResponse response = new MyResponse();
		response.setUser( utils.convertUserToResponse(user , true) );
		response.setStatusCode(200);
		
		return response;
	}
	
	//----------------------------------------------------------------

	@Override
	public MyResponse updateUser(String userId, UserRequest nUser) {

		UserEntity user = urepo.findByUserId(userId)
				.orElseThrow( () -> new ResourceNotFoundException("user not found  :  " + userId));
		
		user.setName(nUser.getName());
		user.setEmail(nUser.getEmail());
		user.setPassword(nUser.getPassword());
		user.setRole(nUser.getRole());
		
		urepo.save(user);
		MyResponse response = new MyResponse();
		response.setUser( utils.convertUserToResponse(user , false) );
		response.setStatusCode(200);
		
		return response;
	}
	
	//----------------------------------------------------------------

	@Override
	public MyResponse getUserBookings(String userId) {

		UserEntity user = urepo.findByUserId(userId)
				.orElseThrow( () -> new ResourceNotFoundException("user not found  :  " + userId));
		
		MyResponse response = new MyResponse();
		response.setStatusCode(200);
		response.setBookingsList( user.getBookingsList().stream()
				.map( ent -> utils.convertBookingToResponsePlusUserAndEvent(false, true, ent))
				.collect(Collectors.toList()) );
		
		return  response;
	}
	
	//----------------------------------------------------------------

	@Override
	public String getUserRole(String email) {

		UserEntity user = urepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user with email not found : " + email));
		return user.getRole();
	}

	//----------------------------------------------------------------
	
	@Override
	public MyResponse getUserByEmail(String email) {

		UserEntity user = urepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user with email not found : " + email));
		
		MyResponse response = new MyResponse();
		response.setUser(utils.convertUserToResponse(user, false));
		response.setStatusCode(200);
		
		return response;
	}

}
