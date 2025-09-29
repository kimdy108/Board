package com.project.board.main.api.domain.setting;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_main_setting", indexes = {
        @Index(name = "idx_setting_key", columnList = "setting_key")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainSetting {
    @Id
    @Column(name = "setting_key", length = 50, unique = true, nullable = false, updatable = false)
    private String settingKey;

    @Column(name = "setting_val", columnDefinition = "TEXT")
    private String settingVal;

    @Builder
    public BoardMainSetting(String settingKey, String settingVal) {
        this.settingKey = settingKey;
        this.settingVal = settingVal;
    }
}
