package com.BridgeIt.FundooApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.BridgeIt.FundooApp.response.Response;



@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(String message) {
		Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
		return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleUserException(UserException userException) {
		Response response = new Response(HttpStatus.BAD_REQUEST.value(), userException.getMessage(), null);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

}
