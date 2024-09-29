package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.auth.AuthTokenBase;
import com.project.board.main.api.dto.auth.RefreshAuthToken;
import com.project.board.main.api.dto.member.*;
import com.project.board.main.api.repository.member.BoardMemberRepository;
import com.project.board.main.api.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardMemberService {
    private final BoardMemberRepository boardMemberRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardMemberSuccessLogin login(BoardMemberLogin boardMemberLogin) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndUseFlag(boardMemberLogin.getId(), true)
                .orElseThrow(() -> new RuntimeException("loginFail"));
        if (!passwordEncoder.matches(boardMemberLogin.getPassword(), boardMember.getMemberPassword())) throw new RuntimeException("loginFail");

        AuthTokenBase authTokenBase = AuthTokenBase.create(boardMember.getMemberId(),
                boardMember.getMemberPassword(),
                boardMember.getMemberName(),
                boardMember.getMemberNickName(),
                boardMember.getMemberRole());

        return BoardMemberSuccessLogin.create(jwtUtil.createAccessToken(authTokenBase),
                passwordEncoder.encode(boardMember.getMemberId() + "@" + boardMember.getMemberName() + "@" + boardMember.getMemberNickName()),
                boardMember.getMemberId(),
                boardMember.getMemberGuid(),
                boardMember.getMemberRole());
    }

    @Transactional
    public String join(BoardMemberJoin boardMemberJoin) {
        String result;
        try {
            BoardMember boardMember = BoardMember.create(UUID.randomUUID().toString(),
                    boardMemberJoin.getId(),
                    passwordEncoder.encode(boardMemberJoin.getPassword()),
                    boardMemberJoin.getName(),
                    boardMemberJoin.getNickName(),
                    boardMemberJoin.getPhone(),
                    boardMemberJoin.getEmail(),
                    "member");
            boardMemberRepository.save(boardMember);
            result = "success";
        } catch (Exception e) {
            e.getStackTrace();
            result = "fail";
        }
        return result;
    }

    @Transactional
    public BoardMember getBoardMemberForResetPassword(String id, String memberPhone) {
        return boardMemberRepository.findBoardMemberByMemberIdAndMemberPhone(id, memberPhone)
                .orElseThrow(() -> new RuntimeException("NoMember"));
    }

    @Transactional
    public void updatePassword(BoardMemberChangePassword boardMemberChangePassword) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(boardMemberChangePassword.getUserGuid())
                .orElseThrow(() -> new RuntimeException("NoMember"));
        boardMember.updatePassword(passwordEncoder.encode(boardMemberChangePassword.getPassword()));
    }

    @Transactional
    public boolean checkJoinToId(String userId) {
        int check = boardMemberRepository.countBoardMemberByMemberId(userId);
        return check == 0;
    }

    @Transactional
    public boolean checkJoinToNickName(String userNickName) {
        int check = boardMemberRepository.countBoardMemberByMemberNickName(userNickName);
        return check == 0;
    }

    @Transactional
    public boolean checkJoinToPhone(String userPhone) {
        int check = boardMemberRepository.countBoardMemberByMemberPhone(userPhone);
        return check == 0;
    }

    @Transactional
    public BoardMemberSuccessRefreshToken refreshAuth(RefreshAuthToken refreshAuthToken) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndUseFlag(refreshAuthToken.getId(), true)
                .orElseThrow(() -> new RuntimeException("authFail"));
        if (!passwordEncoder.matches(boardMember.getMemberId() + "@" + boardMember.getMemberName() + "@" + boardMember.getMemberNickName(), refreshAuthToken.getRefreshToken())) throw new RuntimeException("authFail");

        AuthTokenBase authTokenBase = AuthTokenBase.create(boardMember.getMemberId(),
                boardMember.getMemberPassword(),
                boardMember.getMemberName(),
                boardMember.getMemberNickName(),
                boardMember.getMemberRole());

        return BoardMemberSuccessRefreshToken.create(jwtUtil.createAccessToken(authTokenBase));
    }
}
