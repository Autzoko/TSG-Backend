package com.example.tsgbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class TsgBackendApplicationTests {

    @Test
    void encode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("20020725llt");
        System.out.println(pwd);
    }

}
