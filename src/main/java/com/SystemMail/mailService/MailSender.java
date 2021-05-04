package com.SystemMail.mailService;

import com.SystemMail.config.MailProperties;
import com.SystemMail.dto.MailInfoDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailSender {

    private final MailProperties mailProperties;


    public void sender(MailInfoDto mailInfoDto) {


    }
}
