package com.project.board.main.api.controller.qna;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.qna.answer.QnAAnswerRegist;
import com.project.board.main.api.dto.qna.answer.QnAAnswerUpdate;
import com.project.board.main.api.service.qna.QnAAnswerService;
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
@RequestMapping("/api/board/qna/answer")
@Tag(name = "QnA 답변 관리 컨트롤러", description = "QnA Answer API Controller 입니다.")
public class QnAAnswerController {
    private final JWTUtil jwtUtil;

    private final QnAAnswerService qnaAnswerService;

    @Operation(summary = "answer regist", description = "답변 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> answerRegist(@RequestBody QnAAnswerRegist qnaAnswerRegist, HttpServletRequest request) {
        qnaAnswerService.answerRegist(qnaAnswerRegist, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "answer update", description = "답변 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> answerUpdate(@RequestBody QnAAnswerUpdate qnaAnswerUpdate, HttpServletRequest request) {
        qnaAnswerService.answerUpdate(qnaAnswerUpdate, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "answer delete", description = "답변 삭제")
    @DeleteMapping("/delete/{answerUUID}")
    public ResponseEntity<ResponseMsg> answerDelete(@PathVariable UUID answerUUID, HttpServletRequest request) {
        qnaAnswerService.answerDelete(answerUUID, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "answer info", description = "답변 정보")
    @GetMapping("/info/{answerUUID}")
    public ResponseEntity<ResponseMsg> answerInfo(@PathVariable UUID answerUUID, HttpServletRequest request) {
        return ResponseMsg.successResponse(qnaAnswerService.getAnswerInfo(answerUUID, jwtUtil.getUserUUID(request.getHeader("Authorization"))));
    }
}
