package com.SystemMail.config;

import com.SystemMail.mailService.MailHeaderEncoder;
import com.SystemMail.mailService.SocketMailSender;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public SocketMailSender socketMailSender() {
        return new SocketMailSender();
    }
}
