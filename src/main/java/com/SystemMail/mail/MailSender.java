package com.SystemMail.mail;

import com.SystemMail.config.DomainConnectionProperties;
import com.SystemMail.config.MailProperties;
import com.SystemMail.domain.entity.MailInfo;
import com.SystemMail.domain.entity.SendInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@RequiredArgsConstructor
public class MailSender {

    private final MailProperties mailProperties;
    private final DomainConnectionProperties domainConnectionProperties;
    public void sender(List<SendInfo> sendInfo) {


    }
}
