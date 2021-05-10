package com.SystemMail.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class domainMaxConnectionTest {

    @Autowired
    private DomainMaxConnectionInfo domainMaxConnectionInfo;
    @Test
    public void getDomainPropertyTest() {
        HashMap<String, Integer> info = domainMaxConnectionInfo.getDomainConnectionInfo();
        assertThat(info.get("naver.com")).isEqualTo(10);
        assertThat(info.get("nate.com")).isEqualTo(10);
        assertThat(info.get("daum.net")).isEqualTo(6);
        assertThat(info.get("default")).isEqualTo(15);

    }

}