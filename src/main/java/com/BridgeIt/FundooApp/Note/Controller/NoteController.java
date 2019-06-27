package com.BridgeIt.FundooApp.Note.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.Note.Servise.NoteServiseImpl;
import com.BridgeIt.FundooApp.response.Response;



@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	NoteServiseImpl noteServiseImpl;

	@PostMapping("/create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestHeader String token) {
	String messege = noteServiseImpl.createNote(noteDto, token);
	Response response = new Response(HttpStatus.OK.value(), messege, "");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	@PostMapping("/update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestHeader String token,
			@RequestParam(value="noteId") String noteId) 
	{
		String message = noteServiseImpl.updateNote(noteDto, token, noteId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/delete")
	public ResponseEntity<Response>deletNote(@RequestHeader String token ,@RequestParam(value="noteId") String noteId)
	{
		Response response= noteServiseImpl.deleteNote(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@GetMapping("/getNote")
	public List<NoteDto> readsingleNote(@RequestParam String token) {
		List<NoteDto> listnotes=noteServiseImpl.read(token);
		
		return listnotes;
	} 
	@PostMapping("/isArchive")
	public ResponseEntity<Response> isArchive(@RequestParam String noteId,@RequestHeader String token)
	{
		Response response= noteServiseImpl.isarchive(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	@PostMapping("/isPin")
	public ResponseEntity<Response>isPin(@RequestParam String noteId,@RequestHeader String token){
		Response response= noteServiseImpl.isPin(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/isTrash")
	public ResponseEntity<Response>isTrash(@RequestParam String noteId,@RequestHeader String token){
	Response response = noteServiseImpl.istrach(token, noteId);
	return new  ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/AddLabelToNote")
	public ResponseEntity<Response>addLabel(@RequestParam String noteId,@RequestHeader String token,@RequestHeader String labelId)
	{
		Response response = noteServiseImpl.addLabelToNote(noteId, token, labelId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/RemoveLabelToNote")
	public ResponseEntity<Response>removeLabel(@RequestParam String noteId,@RequestHeader String token,@RequestHeader String labelId)
	{
		Response response = noteServiseImpl.removeLable(noteId, token, labelId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
