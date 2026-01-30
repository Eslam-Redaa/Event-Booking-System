package com.demo.IO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MyResponse {

	private int statusCode;
	private String successMessage;
	
	//when error
	private String error;
	private String errorMessage;
	Map<String , String> errorsList = new HashMap<>();
	private Instant errorTime;
	private String errorPath;
	
	//when login
	private String role;
	private String expirationDate;
	private String token;
	
	private String bookingConfirmationCode;
	
	private UserResponse user ;
	private BookingResponse booking;
	private EventResponse event;
	
	private List<UserResponse> usersList = new ArrayList<>();
	private List<BookingResponse> bookingsList = new ArrayList<>();
	private List<EventResponse> eventsList = new ArrayList<>();
}
