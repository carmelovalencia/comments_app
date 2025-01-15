package com.comments.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.dto.CommentDto;
import com.comments.exception.CommentNotFound;
import com.comments.exception.UnauthorizedDeleteComment;
import com.comments.exception.UnauthorizedEditComment;
import com.comments.model.Comment;
import com.comments.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public CommentDto editComment(String currentUser, CommentDto commentDto)
			throws CommentNotFound, UnauthorizedEditComment {
		Comment comment = getCommentById(commentDto.getId());

		if (commentDto.getId() == null || comment == null) {
			throw new CommentNotFound();
		}

		if (!currentUser.equals("admin") && !comment.getAuthor().getUsername().equalsIgnoreCase(currentUser)) {
			throw new UnauthorizedEditComment();
		}

		comment.setComment(commentDto.getComment());
		comment = commentRepository.save(comment);

		commentDto.setAuthor(comment.getAuthor().getUsername());
		return commentDto;
	}

	public void deleteComment(String currentUser, int commentId) throws CommentNotFound, UnauthorizedDeleteComment {
		Comment comment = getCommentById(commentId);

		if (comment == null) {
			throw new CommentNotFound();
		}

		if (!currentUser.equals("admin") && !comment.getAuthor().getUsername().equalsIgnoreCase(currentUser)) {
			throw new UnauthorizedDeleteComment();
		}

		commentRepository.delete(comment);
	}

	public Comment getCommentById(int id) {
		Optional<Comment> comment = commentRepository.findById(id);

		return comment.isPresent() ? comment.get() : null;
	}
}
