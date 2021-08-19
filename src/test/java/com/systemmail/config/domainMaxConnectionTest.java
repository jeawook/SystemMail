package com.systemmail.config;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("도메인별 연결 개수 정보 확인 테스트")
    public void getDomainPropertyTest() {
        HashMap<String, Integer> info = domainConnectionProperties.getDomainConnectionInfo();
        Integer defaultDomainConn = domainConnectionProperties.getDefaultDomainConn();
        assertThat(info.get("naver.com")).isEqualTo(10);
        assertThat(info.get("nate.com")).isEqualTo(10);
        assertThat(info.get("daum.net")).isEqualTo(6);
        assertThat(defaultDomainConn).isEqualTo(10);
    }

}