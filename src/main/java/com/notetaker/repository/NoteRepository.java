package com.notetaker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.notetaker.model.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {

}
