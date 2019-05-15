package com.BridgeIt.FundooApp.Note.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.Note.Servise.NoteServiseImpl;
import com.BridgeIt.FundooApp.user.Model.Response;

@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	NoteServiseImpl noteServiseImpl;

	@PostMapping("/create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestHeader String token) {
		Response response = noteServiseImpl.createNote(noteDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	@PostMapping("/update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestHeader String token,
			@RequestParam(value="noteId") String noteId) 
	{
		Response response = noteServiseImpl.updateNote(noteDto, token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/delete")
	public ResponseEntity<Response>deletNote(@RequestHeader String token ,@RequestParam(value="noteId") String noteId)
	{
		Response response= noteServiseImpl.deleteNote(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
}
