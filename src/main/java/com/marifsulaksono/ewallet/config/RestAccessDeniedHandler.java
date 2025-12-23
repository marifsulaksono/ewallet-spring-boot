package com.marifsulaksono.ewallet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marifsulaksono.ewallet.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

        @Override
        public void handle(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException ex) throws IOException {

                ApiResponse<Void> apiResponse = ApiResponse.error(
                                HttpStatus.FORBIDDEN.value(),
                                ex.getMessage());

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
        }
}
