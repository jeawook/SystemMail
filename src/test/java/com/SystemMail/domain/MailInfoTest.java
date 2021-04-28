package com.SystemMail.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MailInfoTest {

    @Test
    @DisplayName("mailInfo 생성 성공 테스트")
    public void createMailInfoTest() {
        String name = "";
        Email mailFrom = Email.of("mailFrom@nate.com", name);
        Email replyTo = Email.of("replyTo@nate.com", name);
        Email mailTo = Email.of("mailTo@nate.com", name);
        MailInfo mailInfo = MailInfo.builder()
                .mailFrom(mailFrom)
                .mailTo(mailTo)
                .replyTo(replyTo)
                .message("발송메시지")
                .build();
        assertThat(mailInfo.getMailFrom()).isEqualTo(mailFrom);
        assertThat(mailInfo.getMailTo()).isEqualTo(mailTo);
        assertThat(mailInfo.getReplyTo()).isEqualTo(replyTo);
        assertThat(mailInfo.getMessage()).isEqualTo("발송메시지");
    }

    @Test
    @DisplayName("mailInfo 생성 성공 테스트")
    public void createMailInfoErrorTest() {
        String name = "";
        Email mailFrom = Email.of("mailFrom@nate.com", name);
        Email replyTo = Email.of("replyTo@nate.com", name);
        Email mailTo = Email.of("mailTo@nate.com", name);
        Throwable throwable =
                assertThrows(NullPointerException.class, () -> {
                    MailInfo.builder()
                            .mailFrom(mailFrom)
                            .replyTo(replyTo)
                            .message("발송메시지")
                            .build();
                });
        assertThat(throwable.getMessage()).isEqualTo("mailTo가 입력되어야 합니다.");
    }

    

}