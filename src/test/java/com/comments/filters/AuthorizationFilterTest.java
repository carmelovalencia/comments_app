package com.comments.filters;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comments.service.AuthorService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class AuthorizationFilterTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private FilterChain mockChain;

    @InjectMocks
    private AuthorizationFilter authorizationFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilter_ValidCredentials() throws Exception {
        String validUserName = "testUser";
        String validPassword = "testPassword";
        when(mockRequest.getHeader("username")).thenReturn(validUserName);
        when(mockRequest.getHeader("password")).thenReturn(validPassword);
        when(authorService.isValidCredential(validUserName, validPassword)).thenReturn(true);

        authorizationFilter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockRequest).getHeader("username");
        verify(mockRequest).getHeader("password");
        verify(authorService).isValidCredential(validUserName, validPassword);
        verify(mockRequest).setAttribute("author", validUserName);
        verify(mockChain).doFilter(mockRequest, mockResponse);  // Make sure the filter chain is called
    }

    @Test
    public void testDoFilter_InvalidCredentials() throws Exception {
        String invalidUserName = "invalidUser";
        String invalidPassword = "invalidPassword";
        when(mockRequest.getHeader("username")).thenReturn(invalidUserName);
        when(mockRequest.getHeader("password")).thenReturn(invalidPassword);
        when(authorService.isValidCredential(invalidUserName, invalidPassword)).thenReturn(false);

        authorizationFilter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
        verify(mockChain, never()).doFilter(mockRequest, mockResponse);  // Ensure filter chain was not called
    }
}
