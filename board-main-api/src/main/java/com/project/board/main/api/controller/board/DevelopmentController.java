package com.project.board.main.api.controller.board;

import com.project.board.main.api.dto.board.development.DevelopmentRegist;
import com.project.board.main.api.dto.board.development.DevelopmentUpdate;
import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.service.board.development.DevelopmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/development")
@Tag(name = "개발 게시판 컨트롤러", description = "개발 게시판 API Controller 입니다.")
public class DevelopmentController {
    private final DevelopmentService developmentService;

    @Operation(summary = "development regist", description = "개발 게시글 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> developmentRegist(@RequestBody DevelopmentRegist developmentRegist, HttpServletRequest request) {
        developmentService.developmentRegist(developmentRegist, request.getHeader("Authorization"));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "development update", description = "개발 게시글 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> developmentUpdate(@RequestBody DevelopmentUpdate developmentUpdate, HttpServletRequest request) {
        developmentService.developmentUpdate(developmentUpdate, request.getHeader("Authorization"));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "development delete", description = "개발 게시글 삭제")
    @DeleteMapping("/delete/{developmentUUID}")
    public ResponseEntity<ResponseMsg> developmentDelete(@PathVariable UUID developmentUUID, HttpServletRequest request) {
        developmentService.developmentDelete(developmentUUID, request.getHeader("Authorization"));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "development info", description = "개발 게시글 정보")
    @GetMapping("/info/{developmentUUID}")
    public ResponseEntity<ResponseMsg> developmentInfo(@PathVariable UUID developmentUUID, HttpServletRequest request) {
        return ResponseMsg.successResponse(developmentService.developmentInfo(developmentUUID, request.getHeader("Authorization")));
    }

    @Operation(summary = "development list page", description = "개발 게시글 리스트 페이지")
    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> developmentListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue
    ) {
        return ResponseMsg.successResponse(developmentService.developmentListPage(searchType, searchValue));
    }
}
