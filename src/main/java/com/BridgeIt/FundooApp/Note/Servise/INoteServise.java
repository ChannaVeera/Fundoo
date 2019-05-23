package com.BridgeIt.FundooApp.Note.Servise;

import java.util.List;

import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.user.Model.Response;

public interface INoteServise {
	Response createNote (NoteDto noteDto,String token);
	Response updateNote (NoteDto noteDto, String token,String noteId);
	Response deleteNote ( String token,String noteId);
	List<NoteDto> read(String token);

}
