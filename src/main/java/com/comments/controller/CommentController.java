package com.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comments.dto.CommentDto;
import com.comments.exception.CommentNotFound;
import com.comments.exception.UnauthorizedDeleteComment;
import com.comments.exception.UnauthorizedEditComment;
import com.comments.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editComment(@RequestAttribute("author") String author,
			@Valid @RequestBody CommentDto commentDto) {

		try {
			commentService.editComment(author, commentDto);
		} catch (CommentNotFound | UnauthorizedEditComment e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok(commentDto);
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editComment(@RequestAttribute("author") String author,
			@RequestParam(value = "id", required = true) Integer commentId) {

		try {
			commentService.deleteComment(author, commentId);
		} catch (CommentNotFound | UnauthorizedDeleteComment e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok("Comment deleted.");
	}
}
