package com.SystemMail.config;

import com.SystemMail.mailService.SocketMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public SocketMailSender socketMailSender() {
        return new SocketMailSender();
    }
}
