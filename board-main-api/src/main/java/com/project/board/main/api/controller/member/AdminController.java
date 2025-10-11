package com.project.board.main.api.controller.member;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.member.admin.AdminRegist;
import com.project.board.main.api.dto.member.admin.AdminUpdate;
import com.project.board.main.api.service.member.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/admin")
@Tag(name = "관리자 관리 컨트롤러", description = "관리자 API Controller 입니다.")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "admin regist", description = "관리자 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> adminRegist(@RequestBody AdminRegist adminRegist) {
        adminService.adminRegist(adminRegist);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "admin update", description = "관리자 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> adminUpdate(@RequestBody AdminUpdate adminUpdate) {
        adminService.adminUpdate(adminUpdate);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "admin reset password", description = "관리자 비밀번호 초기화")
    @PutMapping("/reset/password/{adminUUID}")
    public ResponseEntity<ResponseMsg> adminResetPassword(@PathVariable UUID adminUUID) {
        adminService.adminResetPassword(adminUUID);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "admin delete", description = "관리자 삭제")
    @DeleteMapping("/delete/{adminUUID}")
    public ResponseEntity<ResponseMsg> adminDelete(@PathVariable UUID adminUUID) {
        adminService.adminDelete(adminUUID);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "admin info", description = "관리자 정보")
    @GetMapping("/info/{adminUUID}")
    public ResponseEntity<ResponseMsg> adminInfo(@PathVariable UUID adminUUID) {
        return ResponseMsg.successResponse(adminService.adminInfo(adminUUID));
    }

    @Operation(summary = "admin list page", description = "관리자 리스트 페이지")
    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> adminListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue
    ) {
        return ResponseMsg.successResponse(adminService.adminListPage(searchType, searchValue));
    }
}
