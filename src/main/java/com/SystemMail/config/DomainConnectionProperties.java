package com.SystemMail.config;

import com.SystemMail.mail.ConnectionInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
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
