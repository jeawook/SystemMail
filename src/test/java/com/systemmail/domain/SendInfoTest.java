package com.systemmail.domain;

import com.systemmail.domain.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SendInfoTest {

    @Test
    @DisplayName("메트로 적용 테스트")
    public void createSendInfoTest() {
        MailTemplate mailTemplate = MailTemplate.builder()
                .message("메시지")
                .subject("[$subject$]")
                .content("메일 본문 [$name$]님 안녕하세여 [$name$]은 오늘 [$test$]")
                .user("admin")
                .build();
        Email mailTo = Email.of("mailto@test.com", "mailTo");
        Email mailFrom = Email.of("mailFrom@test.com", "mailFrom");
        Email replyTo = Email.of("replyTo@test.com", "replyTo");
        MailInfo mailInfo = MailInfo.builder()
                .message("테스트메일")
                .mailTo(mailTo)
                .mailFrom(mailFrom)
                .replyTo(replyTo)
                .build();
        Email email = Email.of("test@test.com", "test");
        MailGroup mailGroup = MailGroup.builder()
                .name("테스트그룹")
                .email(email)
                .build();
        SendInfo sendInfo = SendInfo.builder()
                .mailTemplate(mailTemplate)
                .mailInfo(mailInfo)
                .mailGroup(mailGroup)
                .build();
        String macroValue = "name,subject,test";
        String macroData = "박재욱,재목,테스트 발송";
        sendInfo.setMacro(macroValue.split(","),macroData.split(","));
        String s = sendInfo.makeContent();
        assertThat(s).isEqualTo("메일 본문 박재욱님 안녕하세여 박재욱은 오늘 테스트 발송");


    }
}
