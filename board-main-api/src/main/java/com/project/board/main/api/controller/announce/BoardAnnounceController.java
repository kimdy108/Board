package com.project.board.main.api.controller.announce;

import com.project.board.main.api.dto.announce.BoardNotice;
import com.project.board.main.api.dto.announce.BoardNoticeRegist;
import com.project.board.main.api.dto.announce.BoardNoticeUpdate;
import com.project.board.main.api.service.announce.BoardAnnounceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/regist")
    public String registAnnounce(@RequestBody BoardNoticeRegist boardNoticeRegist, HttpServletRequest req) {
        boardAnnounceService.registAnnounce(boardNoticeRegist, req.getHeader("ugd"));
        return "success";
    }

    @PutMapping("/update")
    public String updateAnnounce(@RequestBody BoardNoticeUpdate boardNoticeUpdate) {
        boardAnnounceService.updateAnnounce(boardNoticeUpdate);
        return "success";
    }

    @DeleteMapping("/delete/{noticeGuid}")
    public String deleteAnnounce(@PathVariable("noticeGuid") String noticeGuid) {
        boardAnnounceService.deleteAnnounce(noticeGuid);
        return "success";
    }
}
