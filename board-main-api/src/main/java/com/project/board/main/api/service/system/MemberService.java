package com.project.board.main.api.service.system;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.system.MemberInfo;
import com.project.board.main.api.dto.system.MemberUpdate;
import com.project.board.main.api.dto.system.MemberUpdatePassword;
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
public class MemberService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final BoardMainMemberRepository boardMainMemberRepository;

    @Transactional
    public void memberUpdate(MemberUpdate memberUpdate) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUpdate.getMemberUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (memberUpdate.getMemberNickName().equals(decryptStringSalt(boardMainMember.getMemberNickName()))) {
            boardMainMemberRepository.findBoardMainMemberByMemberNickName(decryptStringSalt(memberUpdate.getMemberNickName())).ifPresent(x -> {
                throw new RuntimeException("존재하는 닉네임입니다.");
            });
        }

        boardMainMember.update(
                encryptString(decryptStringSalt(memberUpdate.getMemberName())),
                decryptStringSalt(memberUpdate.getMemberName()),
                encryptString(decryptStringSalt(memberUpdate.getMemberPhone())),
                encryptString(decryptStringSalt(memberUpdate.getMemberEmail())),
                boardMainMember.getMemberRole(),
                memberUpdate.getMemberDescription()
        );
    }

    @Transactional
    public void memberUpdatePassword(MemberUpdatePassword memberUpdatePassword) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUpdatePassword.getMemberUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (!passwordEncoder.matches(decryptStringSalt(memberUpdatePassword.getOldPassword()), boardMainMember.getMemberPassword())) {
            throw new RuntimeException("이전 비밀번호가 일치하지 않습니다.");
        }

        boardMainMember.updatePassword(passwordEncoder.encode(decryptStringSalt(memberUpdatePassword.getNewPassword())));
    }

    public MemberInfo memberInfo(UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));
        return MemberInfo.create(boardMainMember);
    }
}
