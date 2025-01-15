package com.comments.exception;

public class UnauthorizedEditComment extends Exception {
	public UnauthorizedEditComment() {
		super("Unauthorized comment update");
	}
}
