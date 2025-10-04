package com.project.board.main.api.controller.post;

import com.project.board.main.api.dto.post.BoardList;
import com.project.board.main.api.dto.post.BoardListInfo;
import com.project.board.main.api.dto.post.BoardRegist;
import com.project.board.main.api.dto.post.BoardUpdate;
import com.project.board.main.api.service.post.BoardDevelopmentAndStackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/dev/stack")
public class BoardDevelopmentAndStackController {
    private final BoardDevelopmentAndStackService boardDevelopmentAndStackService;

    @GetMapping("/search/list/all")
    public List<BoardList> listAll() {
        return boardDevelopmentAndStackService.getDevelopmentAndStackListAll();
    }

    @GetMapping("/search")
    public BoardListInfo getList(@RequestParam String developmentAndStackGuid) {
        return boardDevelopmentAndStackService.getDevelopmentAndStack(developmentAndStackGuid);
    }

    @PostMapping("/regist")
    public String registDevelopmentAndStack(@RequestBody BoardRegist boardRegist, HttpServletRequest req) {
        boardDevelopmentAndStackService.registDevelopmentAndStack(boardRegist, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/update")
    public String updateDevelopmentAndStack(@RequestBody BoardUpdate boardUpdate) {
        boardDevelopmentAndStackService.updateDevelopmentAndStack(boardUpdate);
        return "success";
    }

    @DeleteMapping("/delete/{boardGuid}")
    public String deleteDevelopmentAndStack(@PathVariable("boardGuid") String boardGuid) {
        boardDevelopmentAndStackService.deleteDevelopmentAndStack(boardGuid);
        return "success";
    }
}
