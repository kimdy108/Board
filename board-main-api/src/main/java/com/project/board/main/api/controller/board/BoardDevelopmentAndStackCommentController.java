package com.project.board.main.api.controller.board;

import com.project.board.main.api.dto.board.BoardComment;
import com.project.board.main.api.service.board.BoardDevelopmentAndStackCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/dev/stack/comment")
public class BoardDevelopmentAndStackCommentController {
    private final BoardDevelopmentAndStackCommentService boardDevelopmentAndStackCommentService;

    @GetMapping("/list/all")
    public List<BoardComment> listAll(@RequestParam String developmentStackGuid) {
        return boardDevelopmentAndStackCommentService.getBoardCommentList(developmentStackGuid);
    }
}
