package com.project.board.main.api.service.auth;

import com.project.board.main.api.domain.admin.BoardMainAdmin;
import com.project.board.main.api.dto.admin.AdminAuth;
import com.project.board.main.api.dto.admin.AdminLogin;
import com.project.board.main.api.dto.admin.AdminRefresh;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.repository.admin.BoardMainAdminRepository;
import com.project.board.main.api.service.component.RedisService;
import com.project.board.main.api.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.board.main.api.utils.Common.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    private final BoardMainAdminRepository boardMainAdminRepository;

    public AdminAuth login(AdminLogin adminLogin) {
        String adminID = decryptStringSalt(adminLogin.getAdminID());
        String adminPassword = decryptStringSalt(adminLogin.getAdminPassword());

        BoardMainAdmin boardMainAdmin = boardMainAdminRepository.findBoardMainAdminByAdminID(adminID);
        if (boardMainAdmin == null) throw new RuntimeException("아이디 또는 비밀번호를 확인해주세요.");
        if (IsYesNo.NO.equals(boardMainAdmin.getIsActive())) throw new RuntimeException("아이디 또는 비밀번호를 확인해주세요.");
        if (!passwordEncoder.matches(adminPassword, boardMainAdmin.getAdminPassword())) throw new RuntimeException("아이디 또는 비밀번호를 확인해주세요.");

        String accessToken = encryptStringSalt(jwtUtil.createAuthToken(boardMainAdmin.getAdminName(), boardMainAdmin.getAdminID(), boardMainAdmin.getAdminUUID(), boardMainAdmin.getAdminRole()));
        String refreshToken = encryptStringSalt(jwtUtil.createRefreshToken(boardMainAdmin.getAdminID()));
        UUID sessionUUID = UUID.randomUUID();

        redisService.setValues(boardMainAdmin.getAdminID() + "-" + sessionUUID, refreshToken);
        boardMainAdminRepository.updateBoardMainAdminByAdminUUIDForLogin(boardMainAdmin.getAdminUUID(), LocalDateTime.now());

        return AdminAuth.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loginResult(encryptString(
                        boardMainAdmin.getAdminName() + AUTHCHAR +
                                boardMainAdmin.getAdminUUID() + AUTHCHAR +
                                boardMainAdmin.getAdminRole() + AUTHCHAR +
                                sessionUUID))
                .build();
    }

    public String refreshToken(AdminRefresh adminRefresh) {
        String adminAccount = decryptStringSalt(adminRefresh.getAdminAccount());
        String refreshToken = decryptStringSalt(adminRefresh.getRefreshToken());

        String redisRefreshToken = redisService.getValues(adminAccount);
        if (!redisRefreshToken.equals(refreshToken)) throw new RuntimeException("refreshFail");

        String adminID = adminAccount.split("-")[0];
        BoardMainAdmin boardMainAdmin = boardMainAdminRepository.findBoardMainAdminByAdminID(adminID);
        if (boardMainAdmin == null) throw new RuntimeException("refreshFail");

        return encryptStringSalt(jwtUtil.createAuthToken(boardMainAdmin.getAdminName(), boardMainAdmin.getAdminID(), boardMainAdmin.getAdminUUID(), boardMainAdmin.getAdminRole()));
    }
}
