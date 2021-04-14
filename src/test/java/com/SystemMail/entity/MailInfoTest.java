package com.SystemMail.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MailInfoTest {

    @Test
    @DisplayName("mailInfo 생성 성공 테스트")
    public void createMailInfoTest() {
        Email mailFrom = Email.of("mailFrom@nate.com");
        Email replyTo = Email.of("replyTo@nate.com");
        Email mailTo = Email.of("mailTo@nate.com");
        MailInfo mailInfo = MailInfo.builder()
                .mailFrom(mailFrom)
                .mailTo(mailTo)
                .replyTo(replyTo)
                .build();
        assertThat(mailInfo.getMailFrom().equals(mailFrom));
        assertThat(mailInfo.getMailTo().equals(mailTo));
        assertThat(mailInfo.getReplyTo().equals(replyTo));
    }

    

}