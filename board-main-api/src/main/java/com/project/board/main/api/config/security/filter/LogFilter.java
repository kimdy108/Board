package com.project.board.main.api.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        requestWrapper.getInputStream();

        log.info("[REQUEST] {} {} | IP: {} | RequestBody:{}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                getRequestBody(requestWrapper)
        );

        filterChain.doFilter(requestWrapper, responseWrapper);

        log.info("[RESPONSE] {} {} | ResponseBody:{}",
                request.getMethod(),
                request.getRequestURI(),
                new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8)
        );

        responseWrapper.copyBodyToResponse();
    }

    private String getRequestBody(CustomHttpRequestWrapper requestWrapper) {
        byte[] content = requestWrapper.getRequestBody();
        if (content.length == 0) return "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object json = objectMapper.readValue(content, Object.class);
            return objectMapper.writeValueAsString(json);
        } catch (IOException e) {
            return new String(content, StandardCharsets.UTF_8);
        }
    }
}
