package com.SystemMail.mail;

import com.SystemMail.Service.MailResultInfoService;
import com.SystemMail.Service.SendInfoService;
import com.SystemMail.config.DomainConnectionProperties;
import com.SystemMail.config.MailProperties;
import com.SystemMail.domain.entity.MailResultDetail;
import com.SystemMail.domain.entity.MailResultInfo;
import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.dto.HeaderDto;
import com.SystemMail.dto.MailDto;
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
        logger.debug("send : "+sendInfoList.size());
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
                    sendInfo.setComplete();
                } catch (Exception e) {
                    sendInfo.error();
                }
            }
        }

    }

}
