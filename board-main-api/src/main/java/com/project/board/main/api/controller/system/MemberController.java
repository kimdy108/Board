package com.project.board.main.api.controller.system;

import com.project.board.main.api.dto.common.ResponseMsg;
import com.project.board.main.api.dto.system.MemberInfo;
import com.project.board.main.api.dto.system.MemberUpdate;
import com.project.board.main.api.dto.system.MemberUpdatePassword;
import com.project.board.main.api.service.system.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/system/member")
@Tag(name = "시스템 사용자 관리 컨트롤러", description = "시스템 사용자 API Controller 입니다.")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "member update", description = "사용자 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> memberUpdate(@RequestBody MemberUpdate memberUpdate) {
        memberService.memberUpdate(memberUpdate);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "member update password", description = "사용자 비밀번호 변경")
    @PutMapping("/update/password")
    public ResponseEntity<ResponseMsg> memberUpdatePassword(@RequestBody MemberUpdatePassword memberUpdatePassword) {
        memberService.memberUpdatePassword(memberUpdatePassword);
        return ResponseMsg.successResponse("success");
    }

    @Operation(summary = "member info", description = "사용자 정보")
    @GetMapping("/info/{memberUUID}")
    public ResponseEntity<ResponseMsg> getMemberInfo(@PathVariable UUID memberUUID) {
        return ResponseMsg.successResponse(memberService.memberInfo(memberUUID));
    }
}
