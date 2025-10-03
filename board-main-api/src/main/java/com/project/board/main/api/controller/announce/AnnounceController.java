package com.project.board.main.api.controller.announce;

import com.project.board.main.api.dto.announce.AnnounceRegist;
import com.project.board.main.api.dto.announce.AnnounceUpdate;
import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.service.announce.AnnounceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/announce")
@Tag(name = "공지사항 관리 컨트롤러", description = "공지사항 API Controller 입니다.")
public class AnnounceController {
    private final AnnounceService announceService;

    @Operation(summary = "announce regist", description = "공지사항 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> announceRegist(@RequestBody AnnounceRegist announceRegist, HttpServletRequest request) {
        announceService.announceRegist(announceRegist, request.getHeader("Authorization"));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "announce update", description = "공지사항 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> announceUpdate(@RequestBody AnnounceUpdate announceUpdate, HttpServletRequest request) {
        announceService.announceUpdate(announceUpdate, request.getHeader("Authorization"));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "announce delete", description = "공지사항 삭제")
    @DeleteMapping("/delete/{announceUUID}")
    public ResponseEntity<ResponseMsg> announceDelete(@PathVariable UUID announceUUID, HttpServletRequest request) {
        announceService.announceDelete(announceUUID, request.getHeader("Authorization"));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "announce info", description = "공지사항 정보")
    @GetMapping("/info/{announceUUID}")
    public ResponseEntity<ResponseMsg> announceInfo(@PathVariable UUID announceUUID, HttpServletRequest request) {
        return ResponseMsg.successResponse(announceService.announceInfo(announceUUID, request.getHeader("Authorization")));
    }

    @Operation(summary = "announce list page", description = "공지사항 리스트 페이지")
    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> announceListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue
    ) {
        return ResponseMsg.successResponse(announceService.announceListPage(searchType, searchValue));
    }
}
