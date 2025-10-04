package com.project.board.main.api.controller.post;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.constant.post.PostType;
import com.project.board.main.api.dto.post.post.PostRegist;
import com.project.board.main.api.dto.post.post.PostUpdate;
import com.project.board.main.api.service.post.PostService;
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
@RequestMapping("/api/board/post")
@Tag(name = "게시글 관리 컨트롤러", description = "게시글 API Controller 입니다.")
public class PostController {
    private final JWTUtil jwtUtil;

    private final PostService postService;

    @Operation(summary = "post regist", description = "게시글 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> postRegist(@RequestBody PostRegist postRegist, HttpServletRequest request) {
        postService.postRegist(postRegist, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "post update", description = "게시글 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> postUpdate(@RequestBody PostUpdate postUpdate, HttpServletRequest request) {
        postService.postUpdate(postUpdate, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "post delete", description = "게시글 삭제")
    @DeleteMapping("/delete/{postUUID}")
    public ResponseEntity<ResponseMsg> postDelete(@PathVariable UUID postUUID, HttpServletRequest request) {
        postService.postDelete(postUUID, jwtUtil.getUserUUID(request.getHeader("Authorization")));
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "post info", description = "게시글 정보")
    @GetMapping("/info/{postUUID}")
    public ResponseEntity<ResponseMsg> postInfo(@PathVariable UUID postUUID, HttpServletRequest request) {
        return ResponseMsg.successResponse(postService.postInfo(postUUID, jwtUtil.getUserUUID(request.getHeader("Authorization"))));
    }

    @Operation(summary = "post list page", description = "게시글 리스트 페이지")
    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> postListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam PostType postType
            ) {
        return ResponseMsg.successResponse(postService.postListPage(searchType, searchValue, postType));
    }
}
