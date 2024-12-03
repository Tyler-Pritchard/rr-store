package com.rr.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {RrStoreApplication.class})
@ActiveProfiles("test")
public class RrStoreApplicationTests {

    @Test
    void contextLoads() {
        // Ensure context loads.
    }
}
