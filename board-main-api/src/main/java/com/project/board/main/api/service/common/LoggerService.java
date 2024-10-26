package com.project.board.main.api.service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class LoggerService {

    public void writeLogger(String type, String message) {
        String nowdate = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
        switch (type.toUpperCase()) {
            case "TRACE" -> log.trace(nowdate + message);
            case "DEBUG" -> log.debug(nowdate + message);
            case "INFO" -> log.info(nowdate + message);
            case "WARN" -> log.warn(nowdate + message);
            case "ERROR" -> log.error(nowdate + message);
        }
    }
}