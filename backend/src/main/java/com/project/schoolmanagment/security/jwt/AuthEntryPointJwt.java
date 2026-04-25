package com.project.schoolmanagment.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will be responsible for possible authentication errors handling.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
		// error will be logged in console and/or to a file
		LOGGER.error("Unauthorized error : {}" , authException.getMessage());
		//we are setting the content type of the response
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		//we are setting the response status to 401
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		final Map<String,Object> body = new HashMap<>();
		body.put("status",HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error","Unauthorized");
		body.put("message",authException.getMessage());
		body.put("path",request.getServletPath());

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getOutputStream(),body);
	}
}
