package com.BridgeIt.FundooApp.Note.Servise;

import java.util.List;

import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.user.Model.Response;

public interface INoteServise {
	String createNote (NoteDto noteDto,String token);
	String updateNote (NoteDto noteDto, String token,String noteId);
	String deleteNote ( String token,String noteId);
	List<NoteDto> read(String token);
String  isarchive(String token, String noteId) ;
	 String istrach(String token, String noteId);
	 String isPin(String token, String noteId);
	 String addLabelToNote(String noteId, String token, String labelId);
	 String removeLable(String noteId, String token, String labelId);

}
