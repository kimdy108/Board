package com.project.board.main.api.service.auth;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.user.UserAuth;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.user.UserLogin;
import com.project.board.main.api.dto.user.UserRefresh;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.service.component.RedisService;
import com.project.board.main.api.utils.jwt.JWTUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.project.board.main.api.utils.Common.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    private final BoardMainMemberRepository boardMainMemberRepository;

    @Transactional
    public UserAuth login(UserLogin userLogin) {
        String memberID = decryptStringSalt(userLogin.getUserID());
        String memberPassword = decryptStringSalt(userLogin.getUserPassword());

        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberID(memberID)
                .orElseThrow(() -> new RuntimeException("noMember"));
        if (IsYesNo.NO.equals(boardMainMember.getIsActive())) throw new RuntimeException("noIsActive");
        if (!passwordEncoder.matches(memberPassword, boardMainMember.getMemberPassword())) throw new RuntimeException("wrongPassword");
        if (MemberApprovalType.REJECT.equals(boardMainMember.getMemberApproval())) throw new RuntimeException("isReject");
        if (MemberApprovalType.WAIT.equals(boardMainMember.getMemberApproval())) throw new RuntimeException("isWait");

        String accessToken = encryptStringSalt(jwtUtil.createAuthToken(boardMainMember.getMemberName(), boardMainMember.getMemberID(), boardMainMember.getMemberUUID(), boardMainMember.getMemberRole()));
        String refreshToken = encryptStringSalt(jwtUtil.createRefreshToken(boardMainMember.getMemberID()));
        UUID sessionUUID = UUID.randomUUID();

        redisService.setValues(boardMainMember.getMemberID() + "-" + sessionUUID, refreshToken);
        boardMainMember.updateLastDate();

        return UserAuth.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .loginResult(encryptString(
                        decryptString(boardMainMember.getMemberName()) + AUTHCHAR +
                                boardMainMember.getMemberUUID() + AUTHCHAR +
                                boardMainMember.getMemberRole() + AUTHCHAR +
                                sessionUUID))
                .build();
    }

    public String refreshToken(UserRefresh userRefresh) {
        String memberAccount = decryptStringSalt(userRefresh.getUserAccount());
        String refreshToken = decryptStringSalt(userRefresh.getRefreshToken());

        String redisRefreshToken = redisService.getValues(memberAccount);
        if (!redisRefreshToken.equals(refreshToken)) throw new RuntimeException("refreshFail");

        String memberID = memberAccount.split("-")[0];
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberID(memberID)
                .orElseThrow(() -> new RuntimeException("refreshFail"));

        return encryptStringSalt(jwtUtil.createAuthToken(boardMainMember.getMemberName(), boardMainMember.getMemberID(), boardMainMember.getMemberUUID(), boardMainMember.getMemberRole()));
    }
}
