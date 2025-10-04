package com.project.board.main.api.controller.post;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.post.comment.PostCommentRegist;
import com.project.board.main.api.dto.post.comment.PostCommentUpdate;
import com.project.board.main.api.service.post.PostCommentService;
import com.project.board.main.api.utils.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/post/comment")
@Tag(name = "게시글 댓글 관리 컨트롤러", description = "게시글 댓글 API Controller 입니다.")
public class PostCommentController {
    private final JWTUtil jwtUtil;

    private final PostCommentService postCommentService;

    @Operation(summary = "post comment regist", description = "게시글 댓글 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> postCommentRegist(@RequestBody PostCommentRegist postCommentRegist, HttpServletRequest request) {
        postCommentService.postCommentRegist(postCommentRegist, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "post comment update", description = "게시글 댓글 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> postCommentUpdate(@RequestBody PostCommentUpdate postCommentUpdate, HttpServletRequest request) {
        postCommentService.postCommentUpdate(postCommentUpdate, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "post comment delete", description = "게시글 댓글 삭제")
    @DeleteMapping("/delete/{commentUUID}")
    public ResponseEntity<ResponseMsg> postCommentDelete(@PathVariable UUID commentUUID, HttpServletRequest request) {
        postCommentService.postCommentDelete(commentUUID, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }
}
