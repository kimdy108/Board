package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.member.BoardMemberChangePassword;
import com.project.board.main.api.dto.member.BoardMemberJoin;
import com.project.board.main.api.dto.member.BoardMemberLogin;
import com.project.board.main.api.repository.member.BoardMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardMemberService {
    private final BoardMemberRepository boardMemberRepository;

    @Transactional
    public void login(BoardMemberLogin boardMemberLogin) {
        System.out.println(boardMemberLogin.getId());
        System.out.println(boardMemberLogin.getPassword());
        boardMemberRepository.findByMemberIdAndMemberPassword(boardMemberLogin.getId(), boardMemberLogin.getPassword())
                .orElseThrow(() -> new RuntimeException("noMember"));
    }

    @Transactional
    public String join(BoardMemberJoin boardMemberJoin) {
        String result;
        try {
            BoardMember boardMember = BoardMember.create(UUID.randomUUID().toString(),
                    boardMemberJoin.getId(),
                    boardMemberJoin.getPassword(),
                    boardMemberJoin.getName(),
                    boardMemberJoin.getNickName(),
                    boardMemberJoin.getPhone(),
                    boardMemberJoin.getEmail());
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
        boardMember.updatePassword(boardMemberChangePassword.getPassword());
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
}
