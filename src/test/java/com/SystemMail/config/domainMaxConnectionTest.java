package com.SystemMail.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class domainMaxConnectionTest {

    @Autowired
    private DomainConnectionProperties domainConnectionProperties;
    @Test
    public void getDomainPropertyTest() {
        HashMap<String, Integer> info = domainConnectionProperties.getDomainConnectionInfo();
        assertThat(info.get("naver.com")).isEqualTo(10);
        assertThat(info.get("nate.com")).isEqualTo(10);
        assertThat(info.get("daum.net")).isEqualTo(6);
        assertThat(info.get("default")).isEqualTo(15);

    }

}