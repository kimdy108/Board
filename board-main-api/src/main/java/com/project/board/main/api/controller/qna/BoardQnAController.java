package com.project.board.main.api.controller.qna;

import com.project.board.main.api.dto.qna.BoardQnAList;
import com.project.board.main.api.dto.qna.BoardQnARegist;
import com.project.board.main.api.dto.qna.BoardQnAUpdate;
import com.project.board.main.api.service.qna.BoardQnAService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class BoardQnAController {
    private final BoardQnAService boardQnAService;

    @GetMapping("/search/list/all")
    public List<BoardQnAList> listAll() {
        return boardQnAService.getBoardQnaListAll();
    }

    @GetMapping("/search")
    public BoardQnAList getList(@RequestParam String qnaGuid) {
        return boardQnAService.getQna(qnaGuid);
    }

    @PostMapping("/regist")
    public String regist(@RequestBody BoardQnARegist boardQnARegist, HttpServletRequest req) {
        boardQnAService.registQna(boardQnARegist, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody BoardQnAUpdate qnaUpdate) {
        boardQnAService.updateQna(qnaUpdate);
        return "success";
    }

    @DeleteMapping("/delete/{qnaGuid}")
    public String delete(@PathVariable("qnaGuid") String qnaGuid) {
        boardQnAService.deleteQna(qnaGuid);
        return "success";
    }
}
