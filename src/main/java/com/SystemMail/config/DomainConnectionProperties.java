package com.SystemMail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "domain")
@Getter
public class DomainConnectionProperties {
    private HashMap<String, Integer> domainConnectionInfo = new HashMap<>();

    private void addDomain(String domain, int connection) {
        domainConnectionInfo.put(domain,connection);
    }
}
