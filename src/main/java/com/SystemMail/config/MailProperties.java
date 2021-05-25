package com.SystemMail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class MailProperties {

    @NotNull
    private String contentType;

    @NotNull
    private String encoding;

    @NotNull
    private String returnPath;

}
