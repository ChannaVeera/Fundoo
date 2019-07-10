package com.BridgeIt.FundooApp.Note.Servise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.BridgeIt.FundooApp.Label.Model.Label;
import com.BridgeIt.FundooApp.Label.Repository.ILabelRepository;
import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
import com.BridgeIt.FundooApp.Note.Model.Note;
import com.BridgeIt.FundooApp.Note.Respository.INoteRepository;
import com.BridgeIt.FundooApp.Utility.ITokenGenerator;
import com.BridgeIt.FundooApp.Utility.Utility;
import com.BridgeIt.FundooApp.exception.NoteException;
import com.BridgeIt.FundooApp.exception.UserException;
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
	@Autowired
	private ITokenGenerator iTokenGenerator;
	@Autowired
	private IServiceElasticSearch iServiceElasticSearch;

	@Override
	public String createNote(NoteDto noteDto, String token) {
		String id = iTokenGenerator.verifyToken(token);
		Optional<User> optionalUser = iUserRespository.findById(id);
		return optionalUser.filter(user -> user != null).filter(note -> {
			return noteDto.getTitle() != null && noteDto.getDescription() != null;
		}).map(user -> {
			Note note = modelMapper.map(noteDto, Note.class);
			note.setUserId(user.getUserId());
			note.setCreateTime(Utility.todayDate());
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.createNote(note);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return environment.getProperty("note.create.success");
		}).orElseThrow(() -> new UserException(environment.getProperty("user.not.found")));
	}

	@Override
	public String updateNote(NoteDto noteDto, String token, String id) {
		String userId = iTokenGenerator.verifyToken(token);
		Optional<Note> optionalNote = iNoteRepository.findByNoteIdAndUserId(id, userId);
		return optionalNote.filter(note -> {
			return note != null;
		}).map(note -> {
			note.setUpdateTime(Utility.todayDate());
			note.setTitle(noteDto.getTitle());
			note.setDescription(noteDto.getDescription());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return environment.getProperty("note.update.success ");
		}).orElseThrow(() -> new UserException(environment.getProperty("note.update.unsuccess")));

	}

	@Override
	public String deleteNote(String token, String noteId) {
		String id = iTokenGenerator.verifyToken(token);
		Optional<Note> optionalNote = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		return optionalNote.filter(note -> {
			return note.isTrash();
		}).map(note -> {
			iNoteRepository.delete(note);
			try {
				iServiceElasticSearch.deleteNote(noteId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return environment.getProperty("note.delete.success");
		}).orElseThrow(() -> new NoteException(environment.getProperty("note.delete.unsuccess")));

	}

	public List<Note> read(String token) {
		String userid = iTokenGenerator.verifyToken(token);
		List<Note> notes = iNoteRepository.findByUserId(userid);
		return notes;
	}

	public String isarchive(String token, String noteId) {
		String id = iTokenGenerator.verifyToken(token);
		Note note = iNoteRepository.findByNoteIdAndUserId(noteId, id)
				.orElseThrow(() -> new UserException(environment.getProperty("user.not.found")));
		if ((note.isArchive())) {
			note.setPin(false);
			note.setArchive(true);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {

				e.printStackTrace();
			}
			return environment.getProperty("");
		} else {
			note.setArchive(false);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {

				e.printStackTrace();
			}
			return environment.getProperty("");
		}

	}

	public String istrach(String token, String noteId) {
		String id = iTokenGenerator.verifyToken(token);
		Note note = iNoteRepository.findByNoteIdAndUserId(noteId, id)
				.orElseThrow(() -> new UserException(environment.getProperty("user.not.found")));
		if (note.isTrash()) {
			note.setTrash(false);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {

				e.printStackTrace();
			}
			return environment.getProperty("note.untrash");
		} else {
			note.setTrash(true);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return environment.getProperty("note.trash");
		}

	}

	public String isPin(String token, String noteId) {
		String id = iTokenGenerator.verifyToken(token);
		Note note = iNoteRepository.findByNoteIdAndUserId(noteId, id)
				.orElseThrow(() -> new UserException(environment.getProperty("user.not.found")));
		if (note.isArchive()) {
			note.setArchive(false);
			note.setPin(false);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return environment.getProperty("note.pin");

		} else {
			note.setArchive(true);
			note.setPin(false);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			try {
				iServiceElasticSearch.upDateNote(note);
			} catch (Exception e) {

				e.printStackTrace();
			}
			return environment.getProperty("note.unpin");

		}

	}

	@Override
	public String addLabelToNote(String noteId, String token, String labelId) {
		String id = iTokenGenerator.verifyToken(token);
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
					try {
						iServiceElasticSearch.upDateNote(note);
					} catch (Exception e) {

						e.printStackTrace();
					}
					System.out.println("save label in note" + note);
					return environment.getProperty("label.note.add");
				}
			} else if (labels == null) {
				labels = new ArrayList<Label>();
				labels.add(label);
				note.setLabels(labels);
				iNoteRepository.save(note);
				try {
					iServiceElasticSearch.upDateNote(note);
				} catch (Exception e) {

					e.printStackTrace();
				}
				return environment.getProperty("label.note.add");
			}
		}
		return environment.getProperty("note.notfound");
	}

	public String removeLable(String noteId, String token, String labelId) {
		String id = iTokenGenerator.verifyToken(token);
		Optional<User> user = iUserRespository.findById(id);
		Optional<Note> optionalNote = iNoteRepository.findById(noteId);
		Optional<Label> optionalLabel = iLabelRepository.findById(labelId);
		if (!user.isPresent() && optionalLabel.isPresent() && optionalNote.isPresent()) {
			return environment.getProperty("note.notfound");
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
				try {
					iServiceElasticSearch.upDateNote(note);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return environment.getProperty("note.remove.labels");
			}

			return environment.getProperty("note.remove.lables.fail");
		}
	}

	@Override
	public List<Label> getLabelsFromNote(String noteId, String token) {
		String id = iTokenGenerator.verifyToken(token);
		Note note = iNoteRepository.findByNoteIdAndUserId(noteId, id).get();
		if (note.getLabels() != null) {
			List<Label> labels = note.getLabels().stream().collect(Collectors.toList());
			return labels;
		} else {
			return null;
		}

	}

	@Override
	public String addColour(String noteId, String token, String color) {
		String id = iTokenGenerator.verifyToken(token);
		Optional<Note> optionalnote = iNoteRepository.findByNoteIdAndUserId(noteId, id);
		return optionalnote.filter(note -> {
			return note != null;
		}).map(note -> {
			note.setColor(color);
			note.setUpdateTime(Utility.todayDate());
			iNoteRepository.save(note);
			return environment.getProperty("note.set.color");
		}).orElseThrow(() -> new NoteException(environment.getProperty("note.set.colorf")));

	}
}
