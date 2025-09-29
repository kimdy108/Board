package com.project.board.main.api.config;

import com.project.board.main.api.domain.setting.BoardMainSetting;
import com.project.board.main.api.repository.setting.BoardMainSettingRepository;
import com.project.board.main.api.service.component.LoggerService;
import com.project.board.main.api.service.setting.BaseSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent> {
    private final LoggerService loggerService;
    private final BaseSettingService baseSettingService;

    private final BoardMainSettingRepository boardMainSettingRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        BoardMainSetting boardMainSetting = boardMainSettingRepository.findBoardMainSettingBySettingKey("INSTALL")
                .orElse(BoardMainSetting.builder().settingKey("INSTALL").settingVal("NOINSTALL").build());
        if ("NOINSTALL".equals(boardMainSetting.getSettingVal())) baseSettingService.systemInstall();

        loggerService.writeLogger("info", "\n" +
                " 																																	\n " +
                "   ██╗ ██╗    ██████╗  ██████╗  █████╗ ██████╗ ██████╗     ███╗   ███╗ █████╗ ██╗███╗   ██╗     █████╗ ██████╗ ██╗    ██╗ ██╗  	\n " +
                "  ██╔╝██╔╝    ██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔══██╗    ████╗ ████║██╔══██╗██║████╗  ██║    ██╔══██╗██╔══██╗██║    ╚██╗╚██╗ 	\n " +
                " ██╔╝██╔╝     ██████╔╝██║   ██║███████║██████╔╝██║  ██║    ██╔████╔██║███████║██║██╔██╗ ██║    ███████║██████╔╝██║     ╚██╗╚██╗	\n " +
                " ╚██╗╚██╗     ██╔══██╗██║   ██║██╔══██║██╔══██╗██║  ██║    ██║╚██╔╝██║██╔══██║██║██║╚██╗██║    ██╔══██║██╔═══╝ ██║     ██╔╝██╔╝	\n " +
                "  ╚██╗╚██╗    ██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝    ██║ ╚═╝ ██║██║  ██║██║██║ ╚████║    ██║  ██║██║     ██║    ██╔╝██╔╝ 	\n " +
                "   ╚═╝ ╚═╝    ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚═╝     ╚═╝    ╚═╝ ╚═╝  	\n " +
                " 																																	\n ");
        loggerService.writeLogger("info", "Run Board MAIN API Server");
    }
}
