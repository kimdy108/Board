package com.project.board.main.api.controller.qna;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.qna.qna.QnARegist;
import com.project.board.main.api.dto.qna.qna.QnAUpdate;
import com.project.board.main.api.service.qna.QnAService;
import com.project.board.main.api.utils.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/qna")
@Tag(name = "QnA 관리 컨트롤러", description = "QnA API Controller 입니다.")
public class QnAController {
    private final JWTUtil jwtUtil;

    private final QnAService qnaService;

    @Operation(summary = "qna regist", description = "QnA 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> qnaRegist(@RequestBody QnARegist qnaRegist, HttpServletRequest request) {
        qnaService.qnaRegist(qnaRegist, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "qna update", description = "QnA 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> qnaUpdate(@RequestBody QnAUpdate qnaUpdate, HttpServletRequest request) {
        qnaService.qnaUpdate(qnaUpdate, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "qna delete", description = "QnA 삭제")
    @DeleteMapping("/delete/{qnaUUID}")
    public ResponseEntity<ResponseMsg> qnaDelete(@PathVariable UUID qnaUUID, HttpServletRequest request) {
        qnaService.qnaDelete(qnaUUID, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "qna info", description = "QnA 정보")
    @GetMapping("/info/{qnaUUID}")
    public ResponseEntity<ResponseMsg> qnaInfo(@PathVariable UUID qnaUUID) {
        return ResponseMsg.successResponse(qnaService.qnaInfo(qnaUUID));
    }

    @Operation(summary = "qna list page", description = "QnA 리스트 페이지")
    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> qnaListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            HttpServletRequest request
    ) {
        return ResponseMsg.successResponse(qnaService.qnaListPage(searchType, searchValue, jwtUtil.getUserUUID(request.getHeader("Authorization"))));
    }
}
