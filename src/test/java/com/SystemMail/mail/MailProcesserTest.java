package com.SystemMail.mail;

import com.SystemMail.domain.entity.*;
import com.SystemMail.dto.SendInfoDto;
import com.SystemMail.repository.MailGroupRepository;
import com.SystemMail.repository.MailInfoRepository;
import com.SystemMail.repository.MailTemplateRepository;
import com.SystemMail.repository.SendInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailProcessorTest {

    @Autowired
    private MailTemplateRepository mailTemplateRepository;
    @Autowired
    private MailInfoRepository mailInfoRepository;
    @Autowired
    private SendInfoRepository sendInfoRepository;
    @Autowired
    private MailGroupRepository mailGroupRepository;
    @Autowired
    private MailProcessor mailProcessor;

    @Test
    @DisplayName("메일 발송 처리 테스트")
    public void mailProcessorSendTest(){
        MailTemplate mailTemplate = MailTemplate.builder()
                .message("메시지")
                .subject("[$subject$]")
                .content("메일 본문 [$name$]님 안녕하세여 [$name$]은 오늘 [$test$]")
                .user("admin")
                .build();
        Email mailTo = Email.of("pdj13579@nate.com", "mailTo");
        Email mailFrom = Email.of("mailFrom@test.com", "mailFrom");
        Email replyTo = Email.of("replyTo@test.com", "replyTo");
        MailResultInfo mailResultInfo = MailResultInfo.builder().build();
        MailInfo mailInfo = MailInfo.builder()
                .message("테스트메일")
                .mailTo(mailTo)
                .mailFrom(mailFrom)
                .replyTo(replyTo)
                .build();
        Email email = Email.of("pdj13579@nate.com", "test");
        MailGroup mailGroup = MailGroup.builder()
                .name("테스트그룹")
                .email(email)
                .build();
        SendInfo sendInfo = SendInfo.builder()
                .mailTemplate(mailTemplate)
                .mailInfo(mailInfo)
                .mailGroup(mailGroup)
                .build();
        sendInfo.setMailResultInfo(mailResultInfo);

        MailTemplate saveMailTemplate = mailTemplateRepository.save(mailTemplate);
        MailInfo saveMailInfo = mailInfoRepository.save(mailInfo);
        MailGroup saveMailGroup = mailGroupRepository.save(mailGroup);
        SendInfo saveSendInfo = sendInfoRepository.save(sendInfo);
        List<SendInfo> sendInfoList = new ArrayList<>();
        sendInfoList.add(saveSendInfo);
        mailProcessor.send(sendInfoList);

    }


}