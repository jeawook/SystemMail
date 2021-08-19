package com.systemmail.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "domain")
@Getter
public class DomainConnectionProperties {
    /**
     * 발송 도메인 개수 정보
     */
    private HashMap<String, Integer> domainConnectionInfo = new HashMap<>();
    private int defaultDomainConn = 10;
}
