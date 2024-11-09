package com.project.board.main.api.controller.board;

import com.project.board.main.api.dto.board.BoardComment;
import com.project.board.main.api.dto.board.BoardCommentRegist;
import com.project.board.main.api.dto.board.BoardCommentUpdate;
import com.project.board.main.api.service.board.BoardDevelopmentAndStackCommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/regist")
    public String registDevelopmentAndStackComment(@RequestBody BoardCommentRegist boardCommentRegist, HttpServletRequest req) {
        boardDevelopmentAndStackCommentService.registDevAndStackComment(boardCommentRegist, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/update")
    public String updateDevelopmentAndStackComment(@RequestBody BoardCommentUpdate boardCommentUpdate) {
        boardDevelopmentAndStackCommentService.updateDevAndStackComment(boardCommentUpdate);
        return "success";
    }

    @DeleteMapping("/delete/{commentGuid}")
    public String deleteDevelopmentAndStackComment(@PathVariable String commentGuid) {
        boardDevelopmentAndStackCommentService.deleteDevAndStackComment(commentGuid);
        return "success";
    }
}
