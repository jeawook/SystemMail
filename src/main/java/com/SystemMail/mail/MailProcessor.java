package com.SystemMail.mail;

import com.SystemMail.Service.SendInfoService;
import com.SystemMail.config.DomainConnectionProperties;
import com.SystemMail.config.MailProperties;
import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.domain.entity.SendStatus;
import com.SystemMail.dto.HeaderDto;
import com.SystemMail.dto.MailDto;
import com.SystemMail.exception.SMTPException;
import com.SystemMail.repository.SendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


@Component
@RequiredArgsConstructor
public class MailProcessor {

    private final SendInfoService sendInfoService;
    private final MailProperties mailProperties;
    private final SocketMailSender socketMailSender;
    private final DomainConnectionProperties domainConnProperties;
    private static HashMap<String, Integer> connectionInfo = new HashMap<>();
    private static final int maxConn = 10;

    public void send(List<SendInfo> sendInfoList) {
        for (SendInfo sendInfo : sendInfoList) {
            String domain = sendInfo.getMailGroup().getEmail().getDomain();
            int connectionCnt = connectionInfo.getOrDefault(domain, 0);
            int defaultMaxConn = domainConnProperties.getDomainConnectionInfo().getOrDefault("default", maxConn);
            int maxConn = domainConnProperties.getDomainConnectionInfo().getOrDefault(domain,defaultMaxConn);
            if (maxConn > connectionCnt) {
                HeaderDto headerDto = HeaderDto.builder()
                        .mailTo(sendInfo.getMailInfo().getMailTo())
                        .mailFrom(sendInfo.getMailInfo().getMailTo())
                        .replyTo(sendInfo.getMailInfo().getReplyTo())
                        .contentType(mailProperties.getContentType())
                        .encoding(mailProperties.getEncoding())
                        .build();
                MailDto mailDto = MailDto.builder()
                        .headerDto(headerDto)
                        .email(sendInfo.getMailGroup().getEmail())
                        .content(sendInfo.makeContent())
                        .sendInfo(sendInfo)
                        .build();

                try {
                    sendInfo.sending();
                    sendInfoService.saveSendInfo(sendInfo);
                    socketMailSender.send(mailDto);
                } catch (SMTPException e) {
                    sendInfo.error();
                    sendInfoService.saveSendInfo(sendInfo);
                }

            }
        }

    }

}
