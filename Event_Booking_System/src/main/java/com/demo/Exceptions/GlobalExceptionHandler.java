package com.demo.Exceptions;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.demo.IO.MyResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MyResponse> handleNotFound(ResourceNotFoundException ex , HttpServletRequest request)
	{
		MyResponse response = new MyResponse();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setError("Not Found Excp");
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(Instant.now());
		response.setErrorPath(request.getRequestURI());
		
		return new ResponseEntity<>(response , HttpStatus.NOT_FOUND);
	}
	
	//----------------------------------------------------------------------
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyResponse> handleValidationErrors(MethodArgumentNotValidException ex , HttpServletRequest request)
	{
		MyResponse response = new MyResponse();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setError("Some Errors When Checking The Correctnes of Data Enterted..");
		response.setErrorTime(Instant.now());
		response.setErrorPath(request.getRequestURI());
		ex.getBindingResult().getFieldErrors().forEach( err -> response.getErrorsList().put(err.getField() , err.getDefaultMessage()));
		
		return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
	}
	
	//----------------------------------------------------------------------
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<MyResponse> handleContentTypeError(HttpRequestMethodNotSupportedException ex , HttpServletRequest request)
	{
		MyResponse response = new MyResponse();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setError("You Use the Wrong Method in your Request!!");
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(Instant.now());
		response.setErrorPath(request.getRequestURI());
		
		return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
	}
	
	//----------------------------------------------------------------------
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<MyResponse> handleRunetimeEx(RuntimeException ex , HttpServletRequest request){
		
		MyResponse response = new MyResponse();
		response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setError("In the Runtime..");
		response.setErrorMessage(ex.getMessage());
		response.setErrorTime(Instant.now());
		response.setErrorPath(request.getRequestURI());
		
		return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//----------------------------------------------------------------------
	
		@ExceptionHandler(NullPointerException.class)
		public ResponseEntity<MyResponse> handleRunetimeEx(NullPointerException ex , HttpServletRequest request){
			
			MyResponse response = new MyResponse();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setErrorMessage(ex.getMessage());
			response.setErrorTime(Instant.now());
			response.setErrorPath(request.getRequestURI());
			
			return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
}
