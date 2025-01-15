package com.comments.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.comments.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
	public Author findAuthorByUsername(String username);

	public Author findAuthorByUsernameIgnoreCaseAndPassword(String username, String password);
}
