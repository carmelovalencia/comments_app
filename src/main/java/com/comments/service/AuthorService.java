package com.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.model.Author;
import com.comments.repository.AuthorRepository;

@Service
public class AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	public Author getAuthorByUsername(String username) {
		return authorRepository.findAuthorByUsername(username);
	}

	public boolean isValidCredential(String username, String password) {
		Author author = authorRepository.findAuthorByUsernameIgnoreCaseAndPassword(username, password);

		return author != null;
	}
}
