package com.project.board.main.api.service.setting;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.setting.BoardMainSetting;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.repository.setting.BoardMainSettingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.board.main.api.utils.Common.encryptStringSalt;

@Service
@RequiredArgsConstructor
public class BaseSettingService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainSettingRepository boardMainSettingRepository;

    @Transactional
    public void systemInstall() {
        BoardMainSetting boardMainSetting = boardMainSettingRepository.findBoardMainSettingBySettingKey("INSTALL")
                .orElse(BoardMainSetting.builder().settingKey("INSTALL").settingVal("NOINSTALL").build());
        if ("SUCCESS".equals(boardMainSetting.getSettingVal())) return;

        LocalDateTime nowDate = LocalDateTime.now();

        boardMainMemberRepository.save(BoardMainMember.builder()
                .memberUUID(UUID.randomUUID())
                .memberID("master")
                .memberPassword(passwordEncoder.encode("rhksflwk12345!"))
                .memberName(encryptStringSalt("최고관리자"))
                .memberNickName(encryptStringSalt("최고관리자"))
                .memberPhone(encryptStringSalt("01031611450"))
                .memberEmail(encryptStringSalt("kimbrothers123@daum.net"))
                .memberRole(MemberRole.MASTER)
                .memberType("SYSTEM")
                .memberApproval(MemberApprovalType.APPROVE)
                .lastDate(LocalDateTime.parse("2000-01-29T00:00:00"))
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote("master")
                .build());

        boardMainSettingRepository.save(BoardMainSetting.builder().settingKey("INSTALL").settingVal("SUCCESS").build());
        boardMainSettingRepository.save(BoardMainSetting.builder().settingKey("INSTALLDATE").settingVal(String.valueOf(nowDate)).build());
        boardMainSettingRepository.save(BoardMainSetting.builder().settingKey("SYSTEMUUID").settingVal(String.valueOf(UUID.randomUUID())).build());
    }
}
