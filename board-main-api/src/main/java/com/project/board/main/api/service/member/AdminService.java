package com.project.board.main.api.service.member;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.member.admin.AdminInfo;
import com.project.board.main.api.dto.member.admin.AdminListPage;
import com.project.board.main.api.dto.member.admin.AdminRegist;
import com.project.board.main.api.dto.member.admin.AdminUpdate;
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
public class AdminService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final BoardMainMemberRepository boardMainMemberRepository;

    @Transactional
    public void adminRegist(AdminRegist adminRegist) {
        boardMainMemberRepository.findBoardMainMemberByMemberID(decryptStringSalt(adminRegist.getAdminID())).ifPresent(x -> {
            throw new RuntimeException("존재하는 아이디입니다.");
        });

        boardMainMemberRepository.findBoardMainMemberByMemberNickName(decryptStringSalt(adminRegist.getAdminNickName())).ifPresent(x -> {
            throw new RuntimeException("존재하는 닉네임입니다.");
        });
        
        boardMainMemberRepository.save(BoardMainMember.builder()
                .memberID(decryptStringSalt(adminRegist.getAdminID()))
                .memberPassword(passwordEncoder.encode(decryptStringSalt(adminRegist.getAdminPassword())))
                .memberName(encryptString(decryptStringSalt(adminRegist.getAdminName())))
                .memberNickName(decryptStringSalt(adminRegist.getAdminNickName()))
                .memberPhone(encryptString(decryptStringSalt(adminRegist.getAdminPhone())))
                .memberEmail(encryptString(decryptStringSalt(adminRegist.getAdminEmail())))
                .memberRole(adminRegist.getAdminRole())
                .memberType("NORMAL")
                .memberApproval(MemberApprovalType.APPROVE)
                .descriptionNote(adminRegist.getAdminDescription())
                .build());
    }

    @Transactional
    public void adminUpdate(AdminUpdate adminUpdate) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(adminUpdate.getAdminUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 관리자가 없습니다."));

        if (!boardMainMember.getMemberNickName().equals(decryptStringSalt(adminUpdate.getAdminNickName()))) {
            boardMainMemberRepository.findBoardMainMemberByMemberNickName(decryptStringSalt(adminUpdate.getAdminNickName())).ifPresent(x -> {
                throw new RuntimeException("존재하는 닉네임입니다.");
            });
        }

        boardMainMember.update(
                encryptString(decryptStringSalt(adminUpdate.getAdminName())),
                decryptStringSalt(adminUpdate.getAdminNickName()),
                encryptString(decryptStringSalt(adminUpdate.getAdminPhone())),
                encryptString(decryptStringSalt(adminUpdate.getAdminEmail())),
                adminUpdate.getAdminRole(),
                adminUpdate.getAdminDescription()
        );
    }
    
    @Transactional
    public void adminResetPassword(UUID adminUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(adminUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 관리자가 없습니다."));
        
        boardMainMember.updatePassword("rhksflwk1!");
    }
    
    @Transactional
    public void adminDelete(UUID adminUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(adminUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 관리자가 없습니다."));
        
        boardMainMember.updateStatus(IsYesNo.NO);
    }
    
    public AdminInfo adminInfo(UUID adminUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(adminUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 관리자가 없습니다."));
        
        return AdminInfo.create(boardMainMember);
    }
    
    public AdminListPage adminListPage(String searchType, String searchValue) {
        return boardMainMemberRepository.findBoardMainMemberAdminListPage(searchType, searchValue);
    }
}
