package com.demo.IO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

	private String userId;
	
	private String name;
	private String email;
	private String role;
	
	private Timestamp createdAt;
	private List<BookingResponse> bookingsList = new ArrayList<>();
}
