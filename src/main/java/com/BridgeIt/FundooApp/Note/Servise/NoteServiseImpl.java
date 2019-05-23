package com.BridgeIt.FundooApp.Note.Servise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.BridgeIt.FundooApp.Label.Model.Label;
import com.BridgeIt.FundooApp.Label.Repository.ILabelRepository;
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
	private ILabelRepository iLabelRepository;
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
			List<Note> notes = user.getNotes();
			if (!(notes == null)) {
				notes.add(note);
				user.setNotes(notes);
			} else {
				notes = new ArrayList<Note>();
				notes.add(note);
				user.setNotes(notes);
			}
			iUserRespository.save(user);
			Response response = ResponceUtilty.getResponse(200, "", environment.getProperty("note.create.success"));
			return response;

		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("note.create.unsuccess"));
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
			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("note.update.success "));
			return response;

		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("note.update.unsuccess"));
			return response;
		}

	}

	@Override
	public Response deleteNote(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> isNote = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		if (isNote.isPresent()) {
			if (isNote.get().isTrash() == true) {
				iNoteRepository.delete(isNote.get());
				Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("note.delete.success"));
				return response;
			}
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("note.delete.unsuccess"));
			return response;
		}
		
		return null;
	}

	public List<NoteDto> read(String token) {

		String userid = TokenUtility.verifyToken(token);
		List<Note> notes = iNoteRepository.findByUserId(userid);
		List<NoteDto> listnotes = new ArrayList<>();
		for (Note usernotes : notes) {
			NoteDto notesDto = modelMapper.map(usernotes, NoteDto.class);
			System.out.println("notes all fbsvsvbsvn sub ");
			listnotes.add(notesDto);
			System.out.println(listnotes);
		}
		return listnotes;
	}

	public Response isarchive(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setPin(false);
			if ((note.get().isArchive()) == false) {
				note.get().setArchive(true);
			} else {
				note.get().setArchive(false);
			}
			note.get().setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note.get());
			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("note.unarchive"));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("notarchived"));
			return response;
		}

	}

	public Response istrach(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setTrash(true);
			note.get().setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note.get());
			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("note.trash"));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("invalid isTrash"));
			return response;
		}

	}

	public Response isPin(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setArchive(false);
			note.get().setPin(true);
			note.get().setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note.get());
			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("note.pin"));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("invalid Set Pin"));
			return response;
		}

	}

	@SuppressWarnings("unused")
	public Response addLabelToNote(String noteId, String token, String labelId) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> optionalUser = iUserRespository.findById(id);
		Optional<Note> optionalNote = iNoteRepository.findById(noteId);
		Optional<Label> optionalLabel = iLabelRepository.findById(labelId);
		if (optionalUser.isPresent() && optionalLabel.isPresent() && optionalNote.isPresent()) {
			Label label = optionalLabel.get();
			Note note = optionalNote.get();
			System.err.println(label);
			note.setUpdateTime(Utility.todayDate());
			List<Label> labels = note.getLabels();
			if (labels != null) {
				Optional<Label> opLabel = labels.stream().filter(l -> l.getLableName().equals(label.getLableName()))
						.findFirst();
				System.out.println(opLabel);
				if (!opLabel.isPresent()) {
					labels.add(label);
					note.setLabels(labels);
					note = iNoteRepository.save(note);
					System.out.println("save label in note" + note);
					Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("label.note.add"));
					return response;
				}
			} 
			else if (labels== null) {
				labels = new ArrayList<Label>();
				labels.add(label);
				note.setLabels(labels);
				iNoteRepository.save(note);
				}
			else {
				Response response = ResponceUtilty.getResponse(204, "",  environment.getProperty("label.note.add.fail"));

				return response;
			}
		
	}
		Response response = ResponceUtilty.getResponse(204, "0",environment.getProperty("label.note.add"));

	return response;

	}

	public Response removeLable(String noteId, String token, String labelId) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> user = iUserRespository.findById(id);
		Optional<Note> optionalNote = iNoteRepository.findById(noteId);
		Optional<Label> optionalLabel = iLabelRepository.findById(labelId);
		if (!user.isPresent() && optionalLabel.isPresent() && optionalNote.isPresent()) {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("note.notfound"));
			return response;
		} else {
			Label label = optionalLabel.get();
			Note note = optionalNote.get();
			note.setUpdateTime(Utility.todayDate());
			List<Label> labelList = new ArrayList<Label>();
			labelList = note.getLabels();
			if (labelList.stream().filter(l -> l.getLabelId().equals(label.getLabelId())).findFirst().isPresent()) {
				Label findLabel = labelList.stream().filter(l -> l.getLabelId().equals(label.getLabelId())).findFirst()
						.get();
				labelList.remove(findLabel);
				iNoteRepository.save(note);
				Response response = ResponceUtilty.getResponse(200, token,
						environment.getProperty("note.remove.labels"));
				return response;
			}
			Response response = ResponceUtilty.getResponse(204, "0",environment.getProperty("note.remove.lables.fail"));
			return response;
		}
	}
}
