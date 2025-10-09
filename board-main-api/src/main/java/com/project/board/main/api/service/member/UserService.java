package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.member.user.UserSignUp;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.board.main.api.utils.Common.decryptStringSalt;
import static com.project.board.main.api.utils.Common.encryptString;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final BoardMainMemberRepository boardMainMemberRepository;

    @Transactional
    public void userSignUp(UserSignUp userSignUp) {
        if (boardMainMemberRepository.findBoardMainMemberByMemberID(decryptStringSalt(userSignUp.getUserID())).isPresent()) throw new RuntimeException("존재하는 아이디입니다.");

        boardMainMemberRepository.save(BoardMainMember.builder()
                .memberID(decryptStringSalt(userSignUp.getUserID()))
                .memberPassword(passwordEncoder.encode(decryptStringSalt(userSignUp.getUserPassword())))
                .memberName(encryptString(decryptStringSalt(userSignUp.getUserName())))
                .memberNickName(encryptString(decryptStringSalt(userSignUp.getUserNickName())))
                .memberPhone(encryptString(decryptStringSalt(userSignUp.getUserPhone())))
                .memberEmail(encryptString(decryptStringSalt(userSignUp.getUserEmail())))
                .memberRole(MemberRole.MEMBER)
                .memberType("NORMAL")
                .memberApproval(MemberApprovalType.WAIT)
                .descriptionNote("")
                .build());
    }
}
