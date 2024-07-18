package com.notetaker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDto {
	private Integer id;
	private String title;
	private String body;
}
