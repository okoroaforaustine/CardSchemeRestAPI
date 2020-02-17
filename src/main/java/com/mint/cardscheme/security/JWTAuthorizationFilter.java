/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mint.cardscheme.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mint.cardscheme.entity.AppUser;
import com.mint.cardscheme.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 *
 * @author austine.okoroafor
 */
public class JWTAuthorizationFilter  extends BasicAuthenticationFilter  {
      @Autowired UserRepository appUserRepository;
	
	//@Autowired Utils appUtils;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
		                FilterChain chain) throws IOException, ServletException
	{
		String header = req.getHeader(SecurityConstants.HEADER_STRING);
		
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX))
		{
		    chain.doFilter(req, res);
		    return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
	{
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token != null)
		{
		    // parse the token.
			String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
		        .build()
		        .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
		        .getSubject();
		
		       // if(!appUtils.isEmptyString(user) && isValidUser(user))
		        //    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                         if(isValidUser(user))
		            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
		        
		        return null;
		}
		
		return null;
	}
	
	private Boolean isValidUser(String username)
	{
		AppUser user = appUserRepository.findByUsername(username);
		if(null != user && user instanceof AppUser)
			return true;
		return false;
	}
}
