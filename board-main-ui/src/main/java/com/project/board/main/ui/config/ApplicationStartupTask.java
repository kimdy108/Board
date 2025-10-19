package com.project.board.main.ui.config;

import com.project.board.main.ui.service.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent> {
    private final LoggerService loggerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loggerService.writeLogger("info", "\n" +
                " 																																	\n " +
                "   ██╗ ██╗    ██████╗  ██████╗  █████╗ ██████╗ ██████╗     ███╗   ███╗ █████╗ ██╗███╗   ██╗    ██╗   ██╗██╗    ██╗ ██╗  	\n " +
                "  ██╔╝██╔╝    ██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔══██╗    ████╗ ████║██╔══██╗██║████╗  ██║    ██║   ██║██║    ╚██╗╚██╗ 	\n " +
                " ██╔╝██╔╝     ██████╔╝██║   ██║███████║██████╔╝██║  ██║    ██╔████╔██║███████║██║██╔██╗ ██║    ██║   ██║██║     ╚██╗╚██╗	\n " +
                " ╚██╗╚██╗     ██╔══██╗██║   ██║██╔══██║██╔══██╗██║  ██║    ██║╚██╔╝██║██╔══██║██║██║╚██╗██║    ██║   ██║██║     ██╔╝██╔╝	\n " +
                "  ╚██╗╚██╗    ██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝    ██║ ╚═╝ ██║██║  ██║██║██║ ╚████║    ╚██████╔╝██║    ██╔╝██╔╝ 	\n " +
                "   ╚═╝ ╚═╝    ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝     ╚═════╝ ╚═╝    ╚═╝ ╚═╝  	\n " +
                " 																																	\n ");
        loggerService.writeLogger("info", "Run Board MAIN API Server");
    }
}
