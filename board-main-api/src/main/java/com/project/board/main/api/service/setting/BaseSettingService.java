package com.project.board.main.api.service.setting;

import com.project.board.main.api.domain.admin.BoardMainAdmin;
import com.project.board.main.api.domain.setting.BoardMainSetting;
import com.project.board.main.api.dto.constant.admin.AdminRole;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.repository.admin.BoardMainAdminRepository;
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

    private final BoardMainAdminRepository boardMainAdminRepository;
    private final BoardMainSettingRepository boardMainSettingRepository;

    @Transactional
    public void systemInstall() {
        BoardMainSetting boardMainSetting = boardMainSettingRepository.findBoardMainSettingBySettingKey("INSTALL")
                .orElse(BoardMainSetting.builder().settingKey("INSTALL").settingVal("NOINSTALL").build());
        if ("SUCCESS".equals(boardMainSetting.getSettingVal())) return;

        LocalDateTime nowDate = LocalDateTime.now();

        boardMainAdminRepository.save(BoardMainAdmin.builder()
                .adminUUID(UUID.randomUUID())
                .adminID("master")
                .adminPassword(passwordEncoder.encode("chlrhrhksflwk12345!"))
                .adminName(encryptStringSalt("최고관리자"))
                .adminPhone(encryptStringSalt("01031611450"))
                .adminEmail(encryptStringSalt("kimbrothers123@daum.net"))
                .adminRole(AdminRole.MASTER)
                .adminType("SYSTEM")
                .lastDate(LocalDateTime.parse("2000-01-29T00:00:00"))
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote("")
                .build());

        boardMainSettingRepository.save(BoardMainSetting.builder().settingKey("INSTALL").settingVal("SUCCESS").build());
        boardMainSettingRepository.save(BoardMainSetting.builder().settingKey("INSTALLDATE").settingVal(String.valueOf(nowDate)).build());
        boardMainSettingRepository.save(BoardMainSetting.builder().settingKey("SYSTEMUUID").settingVal(String.valueOf(UUID.randomUUID())).build());
    }
}
