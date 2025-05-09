package com.project.board.main.api.controller.board;

import com.project.board.main.api.dto.board.BoardComment;
import com.project.board.main.api.dto.board.BoardCommentRegist;
import com.project.board.main.api.dto.board.BoardCommentUpdate;
import com.project.board.main.api.service.board.BoardFreeCommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/free/comment")
public class BoardFreeCommentController {
    private final BoardFreeCommentService boardFreeCommentService;

    @GetMapping("/list/all")
    public List<BoardComment> listAll(@RequestParam String freeGuid) {
        return boardFreeCommentService.getBoardCommentList(freeGuid);
    }

    @PostMapping("/regist")
    public String registDevelopmentAndStackComment(@RequestBody BoardCommentRegist boardCommentRegist, HttpServletRequest req) {
        boardFreeCommentService.registFreeComment(boardCommentRegist, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/update")
    public String updateDevelopmentAndStackComment(@RequestBody BoardCommentUpdate boardCommentUpdate) {
        boardFreeCommentService.updateFreeComment(boardCommentUpdate);
        return "success";
    }

    @DeleteMapping("/delete/{commentGuid}")
    public String deleteDevelopmentAndStackComment(@PathVariable String commentGuid) {
        boardFreeCommentService.deleteFreeComment(commentGuid);
        return "success";
    }
}
