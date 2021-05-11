package com.SystemMail.mailService;

import com.SystemMail.config.DomainMaxConnectionInfo;
import com.SystemMail.config.MailProperties;
import com.SystemMail.dto.MailInfoDto;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor
public class MailSender {

    private final MailProperties mailProperties;

    private final DomainMaxConnectionInfo domainMaxConnectionInfo;
    public void sender(MailInfoDto mailInfoDto) {


    }
}
