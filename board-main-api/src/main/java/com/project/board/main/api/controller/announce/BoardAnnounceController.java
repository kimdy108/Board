package com.project.board.main.api.controller.announce;

import com.project.board.main.api.dto.announce.BoardNotice;
import com.project.board.main.api.service.announce.BoardAnnounceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class BoardAnnounceController {
    private final BoardAnnounceService boardAnnounceService;

    @GetMapping("/search/list/all")
    public List<BoardNotice> listAll() {
        return boardAnnounceService.getAnnounceListAll();
    }
}
