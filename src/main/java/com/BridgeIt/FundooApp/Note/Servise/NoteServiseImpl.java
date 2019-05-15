package com.BridgeIt.FundooApp.Note.Servise;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.Note.Model.Note;
import com.BridgeIt.FundooApp.Note.Respository.INoteRepository;
import com.BridgeIt.FundooApp.Utility.ResponceUtilty;
import com.BridgeIt.FundooApp.Utility.TokenUtility;
import com.BridgeIt.FundooApp.Utility.Utility;
import com.BridgeIt.FundooApp.user.Model.Response;
import com.BridgeIt.FundooApp.user.Model.User;
import com.BridgeIt.FundooApp.user.Respository.IUserRespository;

@Service
public class NoteServiseImpl implements INoteServise {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Environment environment;
	@Autowired
	private INoteRepository iNoteRepository;

	@Autowired
	private IUserRespository iUserRespository;

	@Override
	public Response createNote(NoteDto noteDto, String token) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> isUser = iUserRespository.findById(id);
		if (isUser.isPresent()) {
			User user = iUserRespository.findById(id).get();
			Note note = modelMapper.map(noteDto, Note.class);
			note.setUserId(user.getUserId());
			note.setCreateTime(Utility.todayDate());
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			Response response = ResponceUtilty.getResponse(200, "",environment.getProperty("svalid "));
			return response;

		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("invalid "));
			return response;
		}
	}

	@Override
	public Response updateNote(NoteDto noteDto, String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> isNote = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		if (isNote.isPresent()) {
			isNote.get().setUpdateTime(Utility.todayDate());
			isNote.get().setTitle(noteDto.getTitle());
			isNote.get().setDescription(noteDto.getDescription());
			isNote.get().setCreateTime(Utility.todayDate());
			iNoteRepository.save(isNote.get());
			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("validation "));
			return response;

		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("validation not done"));
			return response;
		}

	}

	@Override
	public Response deleteNote(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> isNote = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		if (isNote.isPresent()) {
			isNote.get().setTrash(true);
			iNoteRepository.delete(isNote.get());
			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("deleted "));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty(""));
			return response;
		}
	}
}
