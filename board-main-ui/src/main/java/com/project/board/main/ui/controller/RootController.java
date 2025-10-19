package com.project.board.main.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping({"/","/error"})
    public String redirectRoot() {
        return "index.html";
    }

    public String getErrorPath() {  return "/error"; }
}
