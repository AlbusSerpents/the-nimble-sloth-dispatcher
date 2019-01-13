package com.nimble.sloth.dispatcher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimble.sloth.dispatcher.func.exceptions.ErrorResponse;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.logging.LogFactory.getLog;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Primary
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final Log logger = getLog(AuthenticationEntryPointImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException) throws IOException {

        final String errorMessage = String.format(" Authentication error: %s", authException.getMessage());
        logger.warn(errorMessage);

        final ErrorResponse errorResponse = new ErrorResponse("Authentication failed");

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), errorResponse);
    }
}
