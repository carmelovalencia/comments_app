package com.comments.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.comments.dto.CommentDto;
import com.comments.exception.CommentNotFound;
import com.comments.exception.UnauthorizedDeleteComment;
import com.comments.exception.UnauthorizedEditComment;
import com.comments.service.CommentService;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    public void testEditComment_Success() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setComment("Updated comment text");

        String currentUser = "user123";


        mockMvc.perform(put("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr("author", currentUser)
                .content("{\"id\":1, \"comment\":\"Updated comment text\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1, \"comment\":\"Updated comment text\"}"));
    }

    @Test
    public void testEditComment_CommentNotFound() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setComment("Updated comment text");

        String currentUser = "user123";

        when(commentService.editComment(eq(currentUser), eq(commentDto)))
                .thenThrow(new CommentNotFound());

        mockMvc.perform(put("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr("author", currentUser)
                .content("{\"id\":1, \"comment\":\"Updated comment text\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditComment_UnauthorizedEdit() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setComment("Updated comment text");

        String currentUser = "user123";

        when(commentService.editComment(eq(currentUser), eq(commentDto)))
                .thenThrow(new UnauthorizedEditComment());

        // Act & Assert
        mockMvc.perform(put("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr("author", currentUser)
                .content("{\"id\":1, \"text\":\"Updated comment text\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteComment_Success() throws Exception {
        String currentUser = "user123";
        Integer commentId = 1;

        doNothing().when(commentService).deleteComment(eq(currentUser), eq(commentId));

        mockMvc.perform(delete("/api/comment")
        		.requestAttr("author", currentUser)
                .param("id", commentId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted."));
    }

    @Test
    public void testDeleteComment_CommentNotFound() throws Exception {
        String currentUser = "user123";
        Integer commentId = 1;

        mockMvc.perform(delete("/api/comment")
        		.requestAttr("author", currentUser)
                .param("id", commentId.toString()))
                .andExpect(status().isOk());
    }
}
