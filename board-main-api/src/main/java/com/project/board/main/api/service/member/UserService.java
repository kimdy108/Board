package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.member.user.UserApproval;
import com.project.board.main.api.dto.member.user.UserInfo;
import com.project.board.main.api.dto.member.user.UserListPage;
import com.project.board.main.api.dto.member.user.UserSignUp;
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
        boardMainMemberRepository.findBoardMainMemberByMemberID(decryptStringSalt(userSignUp.getUserID())).ifPresent(boardMainMember -> {
            throw new RuntimeException("존재하는 아이디입니다.");
        });

        boardMainMemberRepository.findBoardMainMemberByMemberNickName(decryptStringSalt(userSignUp.getUserNickName())).ifPresent(boardMainMember -> {
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
    public void userApproval(UserApproval userApproval) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userApproval.getUserUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        boardMainMember.updateMemberApproval(userApproval.getUserApproval());
    }

    public UserInfo userInfo(UUID userUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));
        return UserInfo.create(boardMainMember);
    }

    public UserListPage userListPage(String searchType, String searchValue) {
        return boardMainMemberRepository.findBoardMainMemberListPage(searchType, searchValue, false);
    }
}
