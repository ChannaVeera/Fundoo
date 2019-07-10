package com.BridgeIt.FundooApp.Note.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BridgeIt.FundooApp.Label.Model.Label;
import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.Note.Model.Note;
import com.BridgeIt.FundooApp.Note.Servise.IServiceElasticSearch;
import com.BridgeIt.FundooApp.Note.Servise.NoteServiseImpl;
import com.BridgeIt.FundooApp.response.Response;



@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	NoteServiseImpl noteServise;
@ Autowired
	IServiceElasticSearch esService;
	@PostMapping("/create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestHeader String token) {
	String messege = noteServise.createNote(noteDto, token);
	Response response = new Response(HttpStatus.OK.value(), messege, "");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	@PostMapping("/update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestHeader String token,
			@RequestParam(value="noteId") String noteId) 
	{
		String message = noteServise.updateNote(noteDto, token, noteId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<Response>deletNote(@RequestHeader String token ,@RequestParam(value="noteId") String noteId)
	{
		String message= noteServise.deleteNote(token, noteId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@GetMapping("/getNote")
	public List<Note> readsingleNote(@RequestParam String token) {
		List<Note> listnotes=noteServise.read(token);
		
		return listnotes;
	} 
	@PostMapping("/isArchive")
	public ResponseEntity<Response> isArchive(@RequestParam String noteId,@RequestHeader String token)
	{
		String message= noteServise.isarchive(token, noteId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	@PostMapping("/isPin")
	public ResponseEntity<Response>isPin(@RequestParam String noteId,@RequestHeader String token){
		String message= noteServise.isPin(token, noteId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/isTrash")
	public ResponseEntity<Response>isTrash(@RequestParam String noteId,@RequestHeader String token){
		String message = noteServise.istrach(token, noteId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
	return new  ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/AddLabelToNote")
	public ResponseEntity<Response>addLabel(@RequestParam String noteId,@RequestHeader String token,@RequestHeader String labelId)
	{
		String message = noteServise.addLabelToNote(noteId, token, labelId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PostMapping("/RemoveLabelToNote")
	public ResponseEntity<Response>removeLabel(@RequestParam String noteId,@RequestHeader String token,@RequestHeader String labelId)
	{
		String message = noteServise.removeLable(noteId, token, labelId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@PutMapping("/color")
	public ResponseEntity<Response> addColour(@RequestParam String noteId, @RequestHeader String token,
			@RequestBody String color) {
		String message = noteServise.addColour(noteId, token, color);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/searchTitle")
	public List<Note> searchTitle(@RequestParam String title, @RequestHeader String token) throws IOException {
		return esService.searchByTitle(title, token);
	}

	@GetMapping("/getlabel")
	public List<Label> getLabel(@RequestParam String noteId, @RequestHeader String token) {
		List<Label> listLabel = noteServise.getLabelsFromNote(noteId, token);
		return listLabel;
	}
}
