package com.comments.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comments.service.AuthorService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter implements Filter {

	@Autowired
	private AuthorService authorService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String userName = req.getHeader("username");
		String password = req.getHeader("password");

		boolean isValidCredential = authorService.isValidCredential(userName, password);

		if (isValidCredential) {
			request.setAttribute("author", userName);
			chain.doFilter(request, response);
		} else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
			return;
		}
	}

}
