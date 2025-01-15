package com.comments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	@JsonProperty(value = "id", required = true)
	@NotNull(message = "Please provide id")
	private Integer id;

	@JsonProperty(value = "comment", required = true)
	@NotNull(message = "Please provide comment")
	private String comment;

	private String author;
}
