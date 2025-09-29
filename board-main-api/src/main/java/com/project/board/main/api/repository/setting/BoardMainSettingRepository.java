package com.project.board.main.api.repository.setting;

import com.project.board.main.api.domain.setting.BoardMainSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardMainSettingRepository extends JpaRepository<BoardMainSetting,String> {
    Optional<BoardMainSetting> findBoardMainSettingBySettingKey(String settingKey);
}
