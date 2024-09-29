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
        String a = Common.decryptString(boardMemberLogin.getId());
        System.out.println(a);
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndUseFlag(Common.decryptString(boardMemberLogin.getId()), true)
                .orElseThrow(() -> new RuntimeException("loginFail"));
        if (!passwordEncoder.matches(Common.decryptString(boardMemberLogin.getPassword()), boardMember.getMemberPassword())) throw new RuntimeException("loginFail");

        AuthTokenBase authTokenBase = AuthTokenBase.create(boardMember.getMemberId(),
                boardMember.getMemberPassword(),
                boardMember.getMemberName(),
                boardMember.getMemberNickName(),
                boardMember.getMemberRole());

        return BoardMemberSuccessLogin.create(jwtUtil.createAccessToken(authTokenBase),
                passwordEncoder.encode(boardMember.getMemberId() + "@" + boardMember.getMemberName() + "@" + boardMember.getMemberNickName()),
                Common.encryptString(boardMember.getMemberId()),
                Common.encryptString(boardMember.getMemberGuid()),
                Common.encryptString(boardMember.getMemberRole()));
    }

    @Transactional
    public String join(BoardMemberJoin boardMemberJoin) {
        BoardMember boardMember = BoardMember.create(UUID.randomUUID().toString(),
                Common.decryptString(boardMemberJoin.getId()),
                passwordEncoder.encode(Common.decryptString(boardMemberJoin.getPassword())),
                Common.decryptString(boardMemberJoin.getName()),
                Common.decryptString(boardMemberJoin.getNickName()),
                Common.decryptString(boardMemberJoin.getPhone()),
                Common.decryptString(boardMemberJoin.getEmail()),
                "member");
        boardMemberRepository.save(boardMember);
        return "success";
    }

    @Transactional
    public BoardMemberCheckChangePassword getBoardMemberForResetPassword(String id, String memberPhone) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndMemberPhone(Common.decryptString(id), Common.decryptString(memberPhone))
                .orElseThrow(() -> new RuntimeException("NoMember"));
        return BoardMemberCheckChangePassword.create(Common.encryptString(boardMember.getMemberId()),
                Common.encryptString(boardMember.getMemberGuid()));
    }

    @Transactional
    public void updatePassword(BoardMemberChangePassword boardMemberChangePassword) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptString(boardMemberChangePassword.getUserGuid()))
                .orElseThrow(() -> new RuntimeException("NoMember"));
        boardMember.updatePassword(passwordEncoder.encode(Common.decryptString(boardMemberChangePassword.getPassword())));
    }

    @Transactional
    public boolean checkJoinToId(String userId) {
        int check = boardMemberRepository.countBoardMemberByMemberId(Common.decryptString(userId));
        return check == 0;
    }

    @Transactional
    public boolean checkJoinToNickName(String userNickName) {
        int check = boardMemberRepository.countBoardMemberByMemberNickName(Common.decryptString(userNickName));
        return check == 0;
    }

    @Transactional
    public boolean checkJoinToPhone(String userPhone) {
        int check = boardMemberRepository.countBoardMemberByMemberPhone(Common.decryptString(userPhone));
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
