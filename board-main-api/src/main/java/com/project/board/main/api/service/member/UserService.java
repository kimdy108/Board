package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.member.user.*;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.project.board.main.api.utils.Common.decryptStringSalt;
import static com.project.board.main.api.utils.Common.encryptString;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final BoardMainMemberRepository boardMainMemberRepository;

    @Transactional
    public void userSignUp(UserSignUp userSignUp) {
        boardMainMemberRepository.findBoardMainMemberByMemberID(decryptStringSalt(userSignUp.getUserID())).ifPresent(x -> {
            throw new RuntimeException("존재하는 아이디입니다.");
        });

        boardMainMemberRepository.findBoardMainMemberByMemberNickName(decryptStringSalt(userSignUp.getUserNickName())).ifPresent(x -> {
            throw new RuntimeException("존재하는 닉네임입니다.");
        });

        boardMainMemberRepository.save(BoardMainMember.builder()
                .memberID(decryptStringSalt(userSignUp.getUserID()))
                .memberPassword(passwordEncoder.encode(decryptStringSalt(userSignUp.getUserPassword())))
                .memberName(encryptString(decryptStringSalt(userSignUp.getUserName())))
                .memberNickName(decryptStringSalt(userSignUp.getUserNickName()))
                .memberPhone(encryptString(decryptStringSalt(userSignUp.getUserPhone())))
                .memberEmail(encryptString(decryptStringSalt(userSignUp.getUserEmail())))
                .memberRole(MemberRole.MEMBER)
                .memberType("NORMAL")
                .memberApproval(MemberApprovalType.WAIT)
                .descriptionNote("")
                .build());
    }

    @Transactional
    public void userUpdate(UserUpdate userUpdate) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userUpdate.getUserUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (!boardMainMember.getMemberNickName().equals(decryptStringSalt(userUpdate.getUserNickName()))) {
            boardMainMemberRepository.findBoardMainMemberByMemberNickName(decryptStringSalt(userUpdate.getUserNickName())).ifPresent(x -> {
                throw new RuntimeException("존재하는 닉네임입니다.");
            });
        }

        boardMainMember.update(
                encryptString(decryptStringSalt(userUpdate.getUserName())),
                decryptStringSalt(userUpdate.getUserNickName()),
                encryptString(decryptStringSalt(userUpdate.getUserPhone())),
                encryptString(decryptStringSalt(userUpdate.getUserEmail())),
                MemberRole.MEMBER,
                userUpdate.getUserDescription()
        );
    }

    @Transactional
    public void userApproval(UserApproval userApproval) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userApproval.getUserUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        boardMainMember.updateMemberApproval(userApproval.getUserApproval());
    }

    @Transactional
    public void userResetPassword(UUID userUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        boardMainMember.updatePassword(passwordEncoder.encode("tkdydwk1!"));
    }

    @Transactional
    public void userDelete(UUID userUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        boardMainMember.updateStatus(IsYesNo.NO);
    }

    public UserInfo userInfo(UUID userUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));
        return UserInfo.create(boardMainMember);
    }

    public UserListPage userListPage(String searchType, String searchValue) {
        return boardMainMemberRepository.findBoardMainMemberUserListPage(searchType, searchValue);
    }
}
