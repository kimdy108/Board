package com.project.board.main.api.config.log;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project.board.main.api.service.component.LoggerService;
import com.project.board.main.api.service.component.TelegramService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LogFilter extends OncePerRequestFilter {
    private final String LOGIN_API = "/api/auth/login";
    private final String REFRESH_API = "/api/auth/refresh";
    private final String SIGNUP_API = "/api/board/user/signup";

    private final ObjectMapper objectMapper;
    private final LoggerService loggerService;
    private final TelegramService telegramService;

    public LogFilter(ObjectMapper objectMapper, LoggerService loggerService, TelegramService telegramService) {
        this.objectMapper = objectMapper;
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);
        this.objectMapper.setDefaultPrettyPrinter(printer);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.loggerService = loggerService;
        this.telegramService = telegramService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        requestWrapper.getInputStream();

        filterChain.doFilter(requestWrapper, responseWrapper);

        int statusCode = responseWrapper.getStatus();
        if (statusCode >= 400 && statusCode != 408) {
            printErrorLog(requestWrapper, responseWrapper, request.getRemoteAddr());
        } else {
            printInfoLog(requestWrapper, responseWrapper, request.getRemoteAddr());
        }

        responseWrapper.copyBodyToResponse();
    }

    private void printInfoLog(CustomHttpRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, String remoteAddr) {
        String printLog = """
                
                --------------------------------------------------------------
                    REST Info Log
                --------------------------------------------------------------
                         URI :: %s
                      Method :: %s
                     Request :: %s
                   Remote IP :: %s
                --------------------------------------------------------------
                 Status Code :: %d
                ResponseBody :: %s
                --------------------------------------------------------------
                """.formatted(
                requestWrapper.getRequestURI(),
                requestWrapper.getMethod(),
                getRequestBody(requestWrapper),
                remoteAddr,
                responseWrapper.getStatus(),
                getResponseBody(responseWrapper));

        loggerService.writeLogger("info", printLog);
    }

    private void printErrorLog(CustomHttpRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, String remoteAddr) {
        String printLog = """
                
                --------------------------------------------------------------
                    REST Error Log
                --------------------------------------------------------------
                         URI :: %s
                      Method :: %s
                     Request :: %s
                   Remote IP :: %s
                --------------------------------------------------------------
                 Status Code :: %d
                ResponseBody :: %s
                --------------------------------------------------------------
                """.formatted(
                requestWrapper.getRequestURI(),
                requestWrapper.getMethod(),
                getRequestBody(requestWrapper),
                remoteAddr,
                responseWrapper.getStatus(),
                getResponseBody(responseWrapper));

        loggerService.writeLogger("error", printLog);
        if (!requestWrapper.getRequestURI().equals(LOGIN_API) && !requestWrapper.getRequestURI().equals(REFRESH_API) && !requestWrapper.getRequestURI().equals(SIGNUP_API)) {
            telegramService.telegramSend(printLog);
        }
    }

    private String getRequestBody(CustomHttpRequestWrapper requestWrapper) {
        byte[] content = requestWrapper.getRequestBody();
        if (content.length == 0) return "";

        try {
            Object json = objectMapper.readValue(content, Object.class);
            return formatJson(objectMapper.writeValueAsString(json));
        } catch (IOException e) {
            return new String(content, StandardCharsets.UTF_8);
        }
    }


    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) {
            return "";
        }
        return formatJson(new String(content, StandardCharsets.UTF_8));
    }

    private String formatJson(String input) {
        int MAX_BODY_CONTENTS_LENGTH = 2000;
        try {
            Object json = objectMapper.readValue(input, Object.class);
            String formattedJson = objectMapper.writeValueAsString(json);
            String[] lines = formattedJson.split("\n");
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append("    ").append(line).append("\n");
            }
            return StringUtils.left(sb.toString().trim(), MAX_BODY_CONTENTS_LENGTH);
        } catch (IOException e) {
            return StringUtils.left(input, MAX_BODY_CONTENTS_LENGTH);
        }
    }
}
