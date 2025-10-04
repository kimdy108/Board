package com.project.board.main.api.controller.post;

import com.project.board.main.api.dto.post.BoardList;
import com.project.board.main.api.dto.post.BoardListInfo;
import com.project.board.main.api.dto.post.BoardRegist;
import com.project.board.main.api.dto.post.BoardUpdate;
import com.project.board.main.api.service.post.BoardFreeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/free")
public class BoardFreeController {
    private final BoardFreeService boardFreeService;

    @GetMapping("/search/list/all")
    public List<BoardList> listAll() {
        return boardFreeService.getFreeListAll();
    }

    @GetMapping("/search")
    public BoardListInfo getList(@RequestParam String freeGuid) {
        return boardFreeService.getFree(freeGuid);
    }

    @PostMapping("/regist")
    public String registFree(@RequestBody BoardRegist boardRegist, HttpServletRequest req) {
        boardFreeService.registFree(boardRegist, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/update")
    public String updateFree(@RequestBody BoardUpdate boardUpdate) {
        boardFreeService.updateFree(boardUpdate);
        return "success";
    }

    @DeleteMapping("/delete/{boardGuid}")
    public String deleteFree(@PathVariable("boardGuid") String boardGuid) {
        boardFreeService.deleteFree(boardGuid);
        return "success";
    }
}
