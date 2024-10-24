package com.project.board.main.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class BoardMainApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardMainApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void whenReady() {
        log.info("\n" +
                "                                                                                       dddddddd                                                                     \n" +
                " BBBBBBBBBBBBBBBBB                                                                     d::::::d                    AAA               PPPPPPPPPPPPPPPPP   IIIIIIIIII \n" +
                " B::::::::::::::::B                                                                    d::::::d                   A:::A              P::::::::::::::::P  I::::::::I \n" +
                " B::::::BBBBBB:::::B                                                                   d::::::d                  A:::::A             P::::::PPPPPP:::::P I::::::::I \n" +
                " BB:::::B     B:::::B                                                                  d:::::d                  A:::::::A            PP:::::P     P:::::PII::::::II \n" +
                "   B::::B     B:::::B   ooooooooooo     aaaaaaaaaaaaa  rrrrr   rrrrrrrrr       ddddddddd:::::d                 A:::::::::A             P::::P     P:::::P  I::::I   \n" +
                "   B::::B     B:::::B oo:::::::::::oo   a::::::::::::a r::::rrr:::::::::r    dd::::::::::::::d                A:::::A:::::A            P::::P     P:::::P  I::::I   \n" +
                "   B::::BBBBBB:::::B o:::::::::::::::o  aaaaaaaaa:::::ar:::::::::::::::::r  d::::::::::::::::d               A:::::A A:::::A           P::::PPPPPP:::::P   I::::I   \n" +
                "   B:::::::::::::BB  o:::::ooooo:::::o           a::::arr::::::rrrrr::::::rd:::::::ddddd:::::d              A:::::A   A:::::A          P:::::::::::::PP    I::::I   \n" +
                "   B::::BBBBBB:::::B o::::o     o::::o    aaaaaaa:::::a r:::::r     r:::::rd::::::d    d:::::d             A:::::A     A:::::A         P::::PPPPPPPPP      I::::I   \n" +
                "   B::::B     B:::::Bo::::o     o::::o  aa::::::::::::a r:::::r     rrrrrrrd:::::d     d:::::d            A:::::AAAAAAAAA:::::A        P::::P              I::::I   \n" +
                "   B::::B     B:::::Bo::::o     o::::o a::::aaaa::::::a r:::::r            d:::::d     d:::::d           A:::::::::::::::::::::A       P::::P              I::::I   \n" +
                "   B::::B     B:::::Bo::::o     o::::oa::::a    a:::::a r:::::r            d:::::d     d:::::d          A:::::AAAAAAAAAAAAA:::::A      P::::P              I::::I   \n" +
                " BB:::::BBBBBB::::::Bo:::::ooooo:::::oa::::a    a:::::a r:::::r            d::::::ddddd::::::dd        A:::::A             A:::::A   PP::::::PP          II::::::II \n" +
                " B:::::::::::::::::B o:::::::::::::::oa:::::aaaa::::::a r:::::r             d:::::::::::::::::d       A:::::A               A:::::A  P::::::::P          I::::::::I \n" +
                " B::::::::::::::::B   oo:::::::::::oo  a::::::::::aa:::ar:::::r              d:::::::::ddd::::d      A:::::A                 A:::::A P::::::::P          I::::::::I \n" +
                " BBBBBBBBBBBBBBBBB      ooooooooooo     aaaaaaaaaa  aaaarrrrrrr               ddddddddd   ddddd     AAAAAAA                   AAAAAAAPPPPPPPPPP          IIIIIIIIII \n" +
                "                                                                                                                                                                    \n" );
    }

}
