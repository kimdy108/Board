package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.auth.AuthTokenBase;
import com.project.board.main.api.dto.auth.RefreshAuthToken;
import com.project.board.main.api.dto.user.*;
import com.project.board.main.api.repository.announce.BoardAnnounceRepository;
import com.project.board.main.api.repository.board.BoardDevelopmentAndStackCommentRepository;
import com.project.board.main.api.repository.board.BoardDevelopmentAndStackRepository;
import com.project.board.main.api.repository.board.BoardFreeCommentRepository;
import com.project.board.main.api.repository.board.BoardFreeRepository;
import com.project.board.main.api.repository.member.BoardMemberRepository;
import com.project.board.main.api.repository.member.BoardMemberRepositoryImpl;
import com.project.board.main.api.utils.Common;
import com.project.board.main.api.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardMemberService {
    private final BoardMemberRepository boardMemberRepository;
    private final BoardAnnounceRepository boardAnnounceRepository;
    private final BoardDevelopmentAndStackRepository boardDevelopmentAndStackRepository;
    private final BoardFreeRepository boardFreeRepository;
    private final BoardDevelopmentAndStackCommentRepository boardDevelopmentAndStackCommentRepository;
    private final BoardFreeCommentRepository boardFreeCommentRepository;

    private final BoardMemberRepositoryImpl boardMemberRepositoryImpl;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardMemberInfo getMemberInfo(String encryptMemberGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptStringSalt(encryptMemberGuid))
                .orElseThrow(() -> new RuntimeException("noUser"));
        return BoardMemberInfo.create(Common.encryptStringSalt(Common.decryptString(boardMember.getMemberId())),
                Common.encryptStringSalt(Common.decryptString(boardMember.getMemberName())),
                Common.encryptStringSalt(boardMember.getMemberNickName()),
                Common.encryptStringSalt(Common.decryptString(boardMember.getMemberEmail())),
                Common.encryptStringSalt(Common.decryptString(boardMember.getMemberPhone())));
    }

    @Transactional
    public List<BoardMemberInfoAll> getMemberInfoAll() {
        List<BoardMemberInfoAll> memberList = boardMemberRepositoryImpl.getBoardMemberInfoAll();
        for (BoardMemberInfoAll member : memberList) {
            member.setUserId(Common.encryptStringSalt(Common.decryptString(member.getUserId())));
            member.setUserGuid(Common.encryptStringSalt(member.getUserGuid()));
            member.setUserName(Common.encryptStringSalt(Common.decryptString(member.getUserName())));
            member.setUserNickName(Common.encryptStringSalt(member.getUserNickName()));
            member.setUserEmail(Common.encryptStringSalt(Common.decryptString(member.getUserEmail())));
            member.setUserPhone(Common.encryptStringSalt(Common.decryptString(member.getUserPhone())));
            member.setUserRole(Common.encryptStringSalt(member.getUserRole()));
        }
        return memberList;
    }

    @Transactional
    public void updateMemberInfo(BoardMemberUpdate boardMemberUpdate, String encryptMemberGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptStringSalt(encryptMemberGuid))
                .orElseThrow(() -> new RuntimeException("noUser"));
        boardMember.updateInfo(Common.encryptString(Common.decryptStringSalt(boardMemberUpdate.getEncryptUserName())),
                Common.encryptString(Common.decryptStringSalt(boardMemberUpdate.getEncryptUserEmail())),
                Common.encryptString(Common.decryptStringSalt(boardMemberUpdate.getEncryptUserPhone())));
    }

    @Transactional
    public void updateMemberPassword(BoardMemberUpdatePassword boardMemberUpdatePassword, String encryptMemberGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptStringSalt(encryptMemberGuid))
                .orElseThrow(() -> new RuntimeException("noUser"));
        if(!passwordEncoder.matches(Common.decryptStringSalt(boardMemberUpdatePassword.getOldPassword()), boardMember.getMemberPassword())) throw new RuntimeException("noMatchPassword");
        else boardMember.updatePassword(passwordEncoder.encode(Common.decryptStringSalt(boardMemberUpdatePassword.getNewPassword())));
    }

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
                Common.encryptStringSalt(boardMember.getMemberNickName()),
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
        boardDevelopmentAndStackRepository.updateUseFlag(userGuid);
        boardFreeRepository.updateUseFlag(userGuid);
        boardDevelopmentAndStackCommentRepository.updateUseFlag(userGuid);
        boardFreeCommentRepository.updateUseFlag(userGuid);
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

    @Transactional
    public void BoardMemberPromoteManager(BoardMemberChangeRoleManager boardMemberChangeRoleManager, String encryptMemberGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptStringSalt(encryptMemberGuid))
                .orElseThrow(() -> new RuntimeException("noUser"));
        if (!"manager".equals(boardMember.getMemberRole())) throw new RuntimeException("noRole");
        BoardMember boardMemberForUpdateManager = boardMemberRepository.findBoardMemberByMemberGuid(boardMemberChangeRoleManager.getUserGuid())
                .orElseThrow(() -> new RuntimeException("noMember"));
        boardMemberForUpdateManager.updateRole("manager");
    }

    @Transactional
    public void BoardMemberRelegateManager(BoardMemberChangeRoleManager boardMemberChangeRoleManager, String encryptMemberGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(Common.decryptStringSalt(encryptMemberGuid))
                .orElseThrow(() -> new RuntimeException("noUser"));
        if (!"manager".equals(boardMember.getMemberRole())) throw new RuntimeException("noRole");
        BoardMember boardMemberForUpdateManager = boardMemberRepository.findBoardMemberByMemberGuid(boardMemberChangeRoleManager.getUserGuid())
                .orElseThrow(() -> new RuntimeException("noMember"));
        boardMemberForUpdateManager.updateRole("member");
    }

    @Transactional
    public BoardMemberInfo getMemberManageInfo(String userGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(userGuid)
                .orElseThrow(() -> new RuntimeException("noUser"));
        return BoardMemberInfo.create(Common.encryptStringSalt(Common.decryptString(boardMember.getMemberId())),
                Common.encryptStringSalt(Common.decryptString(boardMember.getMemberName())),
                Common.encryptStringSalt(boardMember.getMemberNickName()),
                Common.encryptStringSalt(Common.decryptString(boardMember.getMemberEmail())),
                Common.encryptStringSalt(Common.decryptString(boardMember.getMemberPhone())));
    }

    @Transactional
    public String joinMember(BoardMemberJoin boardMemberJoin) {
        String result = "";

        if (!checkJoinToId(boardMemberJoin.getId())) result = "duplicateId";
        else if (!checkJoinToNickName(boardMemberJoin.getNickName())) result = "duplicateNickName";
        else if (!checkJoinToPhone(boardMemberJoin.getPhone())) result = "duplicatePhone";
        else result = join(boardMemberJoin);

        return result;
    }

    @Transactional
    public void updateMemberManageInfo(BoardMemberManageUpdate boardMemberManageUpdate) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(boardMemberManageUpdate.getUserGuid())
                .orElseThrow(() -> new RuntimeException("noUser"));
        boardMember.updateInfo(Common.encryptString(Common.decryptStringSalt(boardMemberManageUpdate.getEncryptUserName())),
                Common.encryptString(Common.decryptStringSalt(boardMemberManageUpdate.getEncryptUserEmail())),
                Common.encryptString(Common.decryptStringSalt(boardMemberManageUpdate.getEncryptUserPhone())));
    }

    @Transactional
    public void resetMemberManagePassword(String userGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(userGuid)
                .orElseThrow(() -> new RuntimeException("NoMember"));
        boardMember.updatePassword(passwordEncoder.encode("tkdydwk1!"));
    }

    @Transactional
    public void deleteMember(String userGuid) {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberGuid(userGuid)
                .orElseThrow(() -> new RuntimeException("noMember"));
        boardMember.updateUseFalg();
        boardDevelopmentAndStackRepository.updateUseFlag(userGuid);
        boardFreeRepository.updateUseFlag(userGuid);
        boardDevelopmentAndStackCommentRepository.updateUseFlag(userGuid);
        boardFreeCommentRepository.updateUseFlag(userGuid);
        // todo: 게시판 및 기타 등등 전부 useFalg -> false 로 해야함..
    }
}
