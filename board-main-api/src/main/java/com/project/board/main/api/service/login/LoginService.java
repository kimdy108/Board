package com.project.board.main.api.service.login;

import com.project.board.main.api.domain.login.BoardMember;
import com.project.board.main.api.dto.login.BoardMemberChangePassword;
import com.project.board.main.api.repository.login.BoardMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final BoardMemberRepository boardMemberRepository;

    @Transactional
    public void login() {
        BoardMember boardMember = BoardMember.create(UUID.randomUUID().toString(),
                "admin",
                "1234",
                "관리자",
                "관리자",
                "01012341234",
                "admin@vizufon.co.kr");
        boardMemberRepository.save(boardMember);
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
        boardMember.updatePassword(boardMemberChangePassword.getPassword());
    }
}
