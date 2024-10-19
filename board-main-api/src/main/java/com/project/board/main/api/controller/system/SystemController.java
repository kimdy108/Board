package com.project.board.main.api.controller.system;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/server")
public class SystemController {
    @GetMapping("/info")
    public String getServerInfo() {
        return "success";
    }
}
