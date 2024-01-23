package br.com.grupo63.serviceidentification;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechchallengeApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(TechchallengeApplicationTests.class);

    @Test
    void contextLoads() {
        logger.info("Testing!");
    }


}
