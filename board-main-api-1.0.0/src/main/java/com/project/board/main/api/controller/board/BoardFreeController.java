package com.project.board.main.api.controller.board;

import com.project.board.main.api.dto.board.BoardList;
import com.project.board.main.api.dto.board.BoardListInfo;
import com.project.board.main.api.dto.board.BoardRegist;
import com.project.board.main.api.dto.board.BoardUpdate;
import com.project.board.main.api.service.board.BoardFreeService;
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
