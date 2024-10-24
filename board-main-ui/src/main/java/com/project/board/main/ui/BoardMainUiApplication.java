package com.project.board.main.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class BoardMainUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardMainUiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void whenReady() {
        log.info("\n " +
                "                                                                                       dddddddd                                     \n " +
                " BBBBBBBBBBBBBBBBB                                                                     d::::::d     UUUUUUUU     UUUUUUUUIIIIIIIIII \n " +
                " B::::::::::::::::B                                                                    d::::::d     U::::::U     U::::::UI::::::::I \n " +
                " B::::::BBBBBB:::::B                                                                   d::::::d     U::::::U     U::::::UI::::::::I \n " +
                " BB:::::B     B:::::B                                                                  d:::::d      UU:::::U     U:::::UUII::::::II \n " +
                "   B::::B     B:::::B   ooooooooooo     aaaaaaaaaaaaa  rrrrr   rrrrrrrrr       ddddddddd:::::d       U:::::U     U:::::U   I::::I   \n " +
                "   B::::B     B:::::B oo:::::::::::oo   a::::::::::::a r::::rrr:::::::::r    dd::::::::::::::d       U:::::D     D:::::U   I::::I   \n " +
                "   B::::BBBBBB:::::B o:::::::::::::::o  aaaaaaaaa:::::ar:::::::::::::::::r  d::::::::::::::::d       U:::::D     D:::::U   I::::I   \n " +
                "   B:::::::::::::BB  o:::::ooooo:::::o           a::::arr::::::rrrrr::::::rd:::::::ddddd:::::d       U:::::D     D:::::U   I::::I   \n " +
                "   B::::BBBBBB:::::B o::::o     o::::o    aaaaaaa:::::a r:::::r     r:::::rd::::::d    d:::::d       U:::::D     D:::::U   I::::I   \n " +
                "   B::::B     B:::::Bo::::o     o::::o  aa::::::::::::a r:::::r     rrrrrrrd:::::d     d:::::d       U:::::D     D:::::U   I::::I   \n " +
                "   B::::B     B:::::Bo::::o     o::::o a::::aaaa::::::a r:::::r            d:::::d     d:::::d       U:::::D     D:::::U   I::::I   \n " +
                "   B::::B     B:::::Bo::::o     o::::oa::::a    a:::::a r:::::r            d:::::d     d:::::d       U::::::U   U::::::U   I::::I   \n " +
                " BB:::::BBBBBB::::::Bo:::::ooooo:::::oa::::a    a:::::a r:::::r            d::::::ddddd::::::dd      U:::::::UUU:::::::U II::::::II \n " +
                " B:::::::::::::::::B o:::::::::::::::oa:::::aaaa::::::a r:::::r             d:::::::::::::::::d       UU:::::::::::::UU  I::::::::I \n " +
                " B::::::::::::::::B   oo:::::::::::oo  a::::::::::aa:::ar:::::r              d:::::::::ddd::::d         UU:::::::::UU    I::::::::I \n " +
                " BBBBBBBBBBBBBBBBB      ooooooooooo     aaaaaaaaaa  aaaarrrrrrr               ddddddddd   ddddd           UUUUUUUUU      IIIIIIIIII \n " +
                "                                                                                                                                    \n " );
    }

}
