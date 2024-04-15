package com.code.ecommerce.security.jwt;

import com.code.ecommerce.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    //    private final HandlerExceptionResolver resolver;
//
//
//    public AuthEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.resolver = resolver;
//    }
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
//        this.resolver.resolveException(request, response, null, authException);
        logger.error("Unauthorized error: {}", authException.getMessage());

        ErrorResponse re =  ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorTime(LocalDateTime.now())
                .errorMessage(authException.getMessage())
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }
}
