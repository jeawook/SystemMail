package com.systemmail.mail;

import com.systemmail.Service.MailResultInfoService;
import com.systemmail.Service.SendInfoService;
import com.systemmail.config.DomainConnectionProperties;
import com.systemmail.config.MailProperties;
import com.systemmail.domain.entity.SendInfo;
import com.systemmail.dto.HeaderDto;
import com.systemmail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Component
@RequiredArgsConstructor
public class MailProcessor {

    private final SendInfoService sendInfoService;
    private final MailResultInfoService resultInfoService;

    private final MailProperties mailProperties;
    private final SocketMailSender socketMailSender;
    private final DomainConnectionProperties domainConnProperties;
    private static HashMap<String, Integer> connectionInfo = new HashMap<>();
    private static final int maxConn = 10;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public void send(List<SendInfo> sendInfoList) {
        logger.debug("send call : "+sendInfoList.size());
        for (SendInfo sendInfo : sendInfoList) {
            logger.debug("mailSend");
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
                        .subject(sendInfo.getMailTemplate().getSubject())
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
                    sendInfo.setComplete();
                } catch (Exception e) {
                    sendInfo.error();
                }
            }
        }

    }

}
