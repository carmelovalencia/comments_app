package com.comments.exception;

public class UnauthorizedDeleteComment extends Exception {
	public UnauthorizedDeleteComment() {
		super("Unauthorized comment deletion");
	}
}
