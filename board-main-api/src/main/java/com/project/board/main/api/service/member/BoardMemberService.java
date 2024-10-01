package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.auth.AuthTokenBase;
import com.project.board.main.api.dto.auth.RefreshAuthToken;
import com.project.board.main.api.dto.member.*;
import com.project.board.main.api.repository.member.BoardMemberRepository;
import com.project.board.main.api.utils.Common;
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
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndUseFlag(Common.encryptString(Common.decryptStringSalt(boardMemberLogin.getId())), true)
                .orElseThrow(() -> new RuntimeException("loginFail"));
        if (!passwordEncoder.matches(Common.decryptStringSalt(boardMemberLogin.getPassword()), boardMember.getMemberPassword())) throw new RuntimeException("loginFail");

        AuthTokenBase authTokenBase = AuthTokenBase.create(Common.decryptString(boardMember.getMemberId()),
                boardMember.getMemberPassword(),
                Common.decryptString(boardMember.getMemberName()),
                boardMember.getMemberNickName(),
                boardMember.getMemberRole());

        return BoardMemberSuccessLogin.create(jwtUtil.createAccessToken(authTokenBase),
                passwordEncoder.encode(boardMember.getMemberId() + "@" + boardMember.getMemberName() + "@" + boardMember.getMemberNickName()),
                Common.encryptStringSalt(boardMember.getMemberId()),
                Common.encryptStringSalt(boardMember.getMemberGuid()),
                Common.encryptStringSalt(boardMember.getMemberRole()));
    }

    @Transactional
    public String join(BoardMemberJoin boardMemberJoin) {
        BoardMember boardMember = BoardMember.create(UUID.randomUUID().toString(),
                Common.encryptString(Common.decryptStringSalt(boardMemberJoin.getId())),
                passwordEncoder.encode(Common.decryptStringSalt(boardMemberJoin.getPassword())),
                Common.encryptString(Common.decryptStringSalt(boardMemberJoin.getName())),
                Common.decryptStringSalt(boardMemberJoin.getNickName()),
                Common.encryptString(Common.decryptStringSalt(boardMemberJoin.getPhone())),
                Common.encryptString(Common.decryptStringSalt(boardMemberJoin.getEmail())),
                "member");
        boardMemberRepository.save(boardMember);
        return "success";
    }

    @Transactional
    public void delete(String userGuid, String userPassword) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(userGuid)
                .orElseThrow(() -> new RuntimeException("noMember"));
        if(!passwordEncoder.matches(Common.decryptStringSalt(userPassword), boardMember.getMemberPassword())) throw new RuntimeException("AuthFail");
        boardMember.updateUseFalg();
        // todo: 게시판 및 기타 등등 전부 useFalg -> false 로 해야함..
    }

    @Transactional
    public BoardMemberCheckChangePassword getBoardMemberForResetPassword(String id, String memberPhone) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndMemberPhone(Common.encryptString(Common.decryptStringSalt(id)), Common.encryptString(Common.decryptStringSalt(memberPhone)))
                .orElseThrow(() -> new RuntimeException("NoMember"));
        return BoardMemberCheckChangePassword.create(Common.encryptStringSalt(Common.decryptString(boardMember.getMemberId())),
                Common.encryptStringSalt(boardMember.getMemberGuid()));
    }

    @Transactional
    public void updatePassword(BoardMemberChangePassword boardMemberChangePassword) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptStringSalt(boardMemberChangePassword.getUserGuid()))
                .orElseThrow(() -> new RuntimeException("NoMember"));
        boardMember.updatePassword(passwordEncoder.encode(Common.decryptStringSalt(boardMemberChangePassword.getPassword())));
    }

    @Transactional
    public boolean checkJoinToId(String userId) {
        int check = boardMemberRepository.countBoardMemberByMemberId(Common.encryptString(Common.decryptStringSalt(userId)));
        return check == 0;
    }

    @Transactional
    public boolean checkJoinToNickName(String userNickName) {
        int check = boardMemberRepository.countBoardMemberByMemberNickName(Common.decryptStringSalt(userNickName));
        return check == 0;
    }

    @Transactional
    public boolean checkJoinToPhone(String userPhone) {
        int check = boardMemberRepository.countBoardMemberByMemberPhone(Common.encryptString(Common.decryptStringSalt(userPhone)));
        return check == 0;
    }

    @Transactional
    public BoardMemberSuccessRefreshToken refreshAuth(RefreshAuthToken refreshAuthToken) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndUseFlag(Common.encryptString(refreshAuthToken.getId()), true)
                .orElseThrow(() -> new RuntimeException("authFail"));
        if (!passwordEncoder.matches(boardMember.getMemberId() + "@" + boardMember.getMemberName() + "@" + boardMember.getMemberNickName(), refreshAuthToken.getRefreshToken())) throw new RuntimeException("authFail");

        AuthTokenBase authTokenBase = AuthTokenBase.create(Common.decryptString(boardMember.getMemberId()),
                boardMember.getMemberPassword(),
                Common.decryptString(boardMember.getMemberName()),
                boardMember.getMemberNickName(),
                boardMember.getMemberRole());

        return BoardMemberSuccessRefreshToken.create(jwtUtil.createAccessToken(authTokenBase));
    }
}
