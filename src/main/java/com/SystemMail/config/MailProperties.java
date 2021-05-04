package com.SystemMail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class MailProperties {

    private String contentType;

    private String encoding;

    private String returnPath;

}
