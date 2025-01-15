package com.comments.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.comments.model.Author;
import com.comments.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author mockAuthor;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	
        // Setup a mock Author object
        mockAuthor = new Author();
        mockAuthor.setUsername("john_doe");
        mockAuthor.setId(1);
    }

    @Test
    public void testGetAuthorByUsernameSuccess() {
        // Given: The repository returns the mockAuthor when findAuthorByUsername is called
        when(userRepository.findAuthorByUsername("john_doe")).thenReturn(mockAuthor);

        // When: The service method is called
        Author author = authorService.getAuthorByUsername("john_doe");

        // Then: Assert that the returned author is the same as the mockAuthor
        assertNotNull(author);
        assertEquals("john_doe", author.getUsername());
        assertEquals(1, author.getId());
    }

    @Test
    public void testGetAuthorByUsernameNotFound() {
        // Given: The repository returns null if the username doesn't exist
        when(userRepository.findAuthorByUsername("non_existent")).thenReturn(null);

        // When: The service method is called
        Author author = authorService.getAuthorByUsername("non_existent");

        // Then: Assert that the returned author is null
        assertNull(author);
    }

    @Test
    public void testGetAuthorByUsernameThrowsException() {
        // Given: The repository throws an exception
        when(userRepository.findAuthorByUsername("error_user")).thenThrow(new RuntimeException("Database error"));

        // When & Then: Verify that the service method throws an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authorService.getAuthorByUsername("error_user");
        });

        assertEquals("Database error", exception.getMessage());
    }
}