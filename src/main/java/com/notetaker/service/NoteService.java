package com.notetaker.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notetaker.dto.NoteDto;
import com.notetaker.model.Note;
import com.notetaker.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	private ModelMapper modelMapper = new ModelMapper();

	public NoteDto saveNote(NoteDto noteDto) {
		Note note = convertToModel(noteDto);

		note = this.noteRepository.save(note);

		return convertToDto(note);
	}

	public List<NoteDto> getAllNotes() {
		List<NoteDto> allNotes = new ArrayList<NoteDto>();

		Iterator<Note> notes = this.noteRepository.findAll().iterator();

		while (notes.hasNext()) {
			allNotes.add(this.convertToDto(notes.next()));
		}

		return allNotes;
	}

	public NoteDto getNoteById(Integer id) {
		Optional<Note> note = this.noteRepository.findById(id);

		if (note.isPresent()) {
			return this.convertToDto(note.get());
		}

		return null;
	}

	public boolean isValidNoteTitle(String title) {
		if (title == null || title.trim().length() == 0 || title.length() > 100) {
			return false;
		}

		return true;
	}

	public boolean isValidNoteBody(String body) {
		if (body == null || body.trim().length() == 0 || body.length() > 1000) {
			return false;
		}

		return true;
	}

	public void deleteNoteById(Integer id) {
		this.noteRepository.deleteById(id);
	}

	public NoteDto convertToDto(Note note) {
		return modelMapper.map(note, NoteDto.class);
	}

	public Note convertToModel(NoteDto noteDto) {
		return modelMapper.map(noteDto, Note.class);
	}
}
