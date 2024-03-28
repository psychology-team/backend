package com.psychology.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-testing.properties")
class ProductApplicationTests {

    @Test
    void contextLoads() {
    }

}
