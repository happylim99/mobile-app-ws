package com.sean.ws.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
			if(authentication == null) {
				chain.doFilter(req, res);
				return;
			}
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
			return;

		} catch (AuthenticationException failed) {
			SecurityContextHolder.clearContext();
			onUnsuccessfulAuthentication(req, res, failed);
			chain.doFilter(req, res);
			return;
		}
	}
	/*
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		//String header = req.getHeader(SecurityConstants.HEADER_STRING);
		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		//  || header.startsWith(SecurityConstants.TOKEN_PREFIX)
		if(authentication != null)
		{
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
		}
		else
		{
			//SecurityContextHolder.getContext().setAuthentication(null);
			System.out.println("null header");
			return;
		}
		} catch (AuthenticationException failed) {
			throw new IOException("hahaha");
		}
	}
	*/
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(SecurityConstants.HEADER_STRING);

		if(token != null) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

			String user = Jwts.parser()
					.setSigningKey(SecurityConstants.getTokenSecret())
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
					
			if(user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}

}
