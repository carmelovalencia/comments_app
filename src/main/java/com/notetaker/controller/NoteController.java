package com.notetaker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.notetaker.dto.NoteDto;
import com.notetaker.service.NoteService;
import com.notetaker.util.MessagesConstants;

@RestController
@RequestMapping("notes")
public class NoteController {
	@Autowired
	private NoteService noteService;

	/**
	 * Add a new note
	 * 
	 * @param noteDto The note dto which contains title and body
	 * @return
	 * 
	 *         BAD_REQUEST: One of the note fields are invalid. OK: returns the
	 *         newly added note dto
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> save(@RequestBody NoteDto noteDto) {
		if (!this.noteService.isValidNoteTitle(noteDto.getTitle())) {
			return ResponseEntity.badRequest().body(MessagesConstants.INVALID_TITLE);
		} else if (!this.noteService.isValidNoteBody(noteDto.getBody())) {
			return ResponseEntity.badRequest().body(MessagesConstants.INVALID_BODY);
		}

		// this is for adding a new note. there should be no id
		noteDto.setId(null);

		noteDto = this.noteService.saveNote(noteDto);

		return ResponseEntity.ok(noteDto);
	}

	/**
	 * Retrieve all notes
	 * 
	 * @return
	 * 
	 *         OK: All notes
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getAllNotes() {
		List<NoteDto> allNotes = this.noteService.getAllNotes();

		return ResponseEntity.ok(allNotes);
	}

	/**
	 * Retrieves a single note by id
	 * 
	 * @param id The id of the note to retrieve
	 * @return
	 * 
	 *         NOT_FOUND: Note by id does not exists OK: The note dto with specific
	 *         id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getNoteById(@PathVariable("id") Integer id) {
		NoteDto noteDto = this.noteService.getNoteById(id);

		if (noteDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note with id: " + id + " does not exists");
		}

		return ResponseEntity.ok(noteDto);
	}

	/**
	 * Updates a note by id
	 * 
	 * @param id      The note id to update
	 * @param noteDto The note that contains the tile and body
	 * @return
	 * 
	 *         BAD_REQUEST: If the note content is invalid. NOT_FOUND: If the note
	 *         does not exists. OK: Returns the updated note dto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateNote(@PathVariable("id") Integer id, @RequestBody NoteDto noteDto) {
		if (!this.noteService.isValidNoteTitle(noteDto.getTitle())) {
			return ResponseEntity.badRequest().body(MessagesConstants.INVALID_TITLE);
		} else if (!this.noteService.isValidNoteBody(noteDto.getBody())) {
			return ResponseEntity.badRequest().body(MessagesConstants.INVALID_BODY);
		}

		NoteDto existingNoteDto = this.noteService.getNoteById(id);

		if (existingNoteDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note with id: " + id + " does not exists");
		} else {
			noteDto.setId(id);
			noteDto = this.noteService.saveNote(noteDto);
			return ResponseEntity.ok(noteDto);
		}
	}

	/**
	 * Deletes a note by id
	 * 
	 * @param id The id of the note to be deleted
	 * @return
	 * 
	 *         NOT_FOUND: If the note does not exists OK: Return a message that the
	 *         note has been deleted
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteNoteById(@PathVariable("id") Integer id) {
		NoteDto existingNoteDto = this.noteService.getNoteById(id);

		if (existingNoteDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note with id: " + id + " does not exists");
		} else {
			this.noteService.deleteNoteById(id);

			return ResponseEntity.ok("Note with id " + id + " deleted.");
		}
	}

}
