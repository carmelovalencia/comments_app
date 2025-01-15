package com.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.model.Author;
import com.comments.repository.UserRepository;

@Service
public class AuthorService {
	@Autowired
	private UserRepository userRepository;

	public Author getAuthorByUsername(String username) {
		return userRepository.findAuthorByUsername(username);
	}

	public boolean isValidCredential(String username, String password) {
		Author author = userRepository.findAuthorByUsernameIgnoreCaseAndPassword(username, password);

		return author != null;
	}
}
