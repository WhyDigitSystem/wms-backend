package com.whydigit.wms.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
		String errorMsg = Objects.nonNull(httpServletRequest.getAttribute("exception"))
				? (String) httpServletRequest.getAttribute("exception")
				: e.getLocalizedMessage();
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMsg);
	}
}
