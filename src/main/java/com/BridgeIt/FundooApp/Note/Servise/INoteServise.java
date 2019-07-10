package com.BridgeIt.FundooApp.Note.Servise;

import java.util.List;

import com.BridgeIt.FundooApp.Label.Model.Label;
import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.Note.Model.Note;

public interface INoteServise {
	String createNote(NoteDto noteDto, String token);

	String updateNote(NoteDto noteDto, String token, String noteId);

	String deleteNote(String token, String noteId);

	List<Note> read(String token);

	String isarchive(String token, String noteId);

	String istrach(String token, String noteId);

	String isPin(String token, String noteId);

	String addLabelToNote(String noteId, String token, String labelId);

	String removeLable(String noteId, String token, String labelId);

	List<Label> getLabelsFromNote(String noteId, String token);

	String addColour(String noteId, String token, String color);
}
