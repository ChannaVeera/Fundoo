package com.BridgeIt.FundooApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.BridgeIt.FundooApp.response.Response;



@ControllerAdvice
public class NoteExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception ex)
	{
		Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
		return new  ResponseEntity<Response>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> handleNoteException(NoteException noteException)
	{
		Response response=new Response(HttpStatus.BAD_REQUEST.value(), noteException.getMessage(), null);
		return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
				
	}
}
