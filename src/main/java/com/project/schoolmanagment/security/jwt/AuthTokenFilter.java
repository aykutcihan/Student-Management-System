package com.project.schoolmanagment.security.jwt;

import com.project.schoolmanagment.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			// 1) From every request get the JWT
			String jwt = parseJwt(request);
			// 2) validate JWT
			if(jwt !=null && jwtUtils.validateJwtToken(jwt)){
				// 3) we need username for this we get this data from JWT
				String userName = jwtUtils.getUserNameFromJwtToken(jwt);
				// 4) check the DB and find the user then upgrade the user to UserDetails
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				request.setAttribute("username",userName);
				// 5) we have userDetails object then we have to send this information to the SPRING SECURITY CONTEXT
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// 6) now spring security context know, who is logged in.
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (UsernameNotFoundException e){
			LOGGER.error("Cannot set user authentication" , e);
		}
		filterChain.doFilter(request,response);
	}

	private String parseJwt(HttpServletRequest request){
		String headerAuth = request.getHeader("Authorization");

		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
			return headerAuth.substring(7);
		}
		return null;
	}
}
