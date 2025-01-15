package com.comments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comments.dto.CommentDto;
import com.comments.exception.CommentNotFound;
import com.comments.exception.UnauthorizedDeleteComment;
import com.comments.exception.UnauthorizedEditComment;
import com.comments.model.Author;
import com.comments.model.Comment;
import com.comments.repository.CommentRepository;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private CommentDto commentDto;
    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        author = new Author();
        author.setUsername("john_doe");

        comment = new Comment();
        comment.setId(1);
        comment.setComment("Original comment");
        comment.setAuthor(author);
        
        commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setComment("Updated comment");
        commentDto.setAuthor("john_doe");
    }

    @Test
    public void testEditComment_Success() throws CommentNotFound, UnauthorizedEditComment {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        
        CommentDto result = commentService.editComment("john_doe", commentDto);
        
        assertNotNull(result);
        assertEquals("Updated comment", result.getComment());
        assertEquals("john_doe", result.getAuthor());
    }

    @Test
    public void testEditComment_CommentNotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(CommentNotFound.class, () -> commentService.editComment("john_doe", commentDto));
    }

    @Test
    public void testEditComment_Unauthorized() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        
        assertThrows(UnauthorizedEditComment.class, () -> commentService.editComment("another_user", commentDto));
    }

    @Test
    public void testDeleteComment_Success() throws CommentNotFound, UnauthorizedDeleteComment {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        
        commentService.deleteComment("john_doe", 1);
        
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    public void testDeleteComment_CommentNotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(CommentNotFound.class, () -> commentService.deleteComment("john_doe", 1));
    }

    @Test
    public void testDeleteComment_Unauthorized() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        
        assertThrows(UnauthorizedDeleteComment.class, () -> commentService.deleteComment("another_user", 1));
    }

    @Test
    public void testGetCommentById_Found() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        
        Comment result = commentService.getCommentById(1);
        
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Original comment", result.getComment());
    }

    @Test
    public void testGetCommentById_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());
        
        Comment result = commentService.getCommentById(1);
        
        assertNull(result);
    }
}