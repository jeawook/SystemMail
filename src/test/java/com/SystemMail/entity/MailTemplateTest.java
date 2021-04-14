package com.SystemMail.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MailTemplateTest {

    @Test
    public void createMailTemplate() {
        MailTemplate mailTemplate = MailTemplate.builder()
                .content("메일 본문")
                .subject("메일 제목")
                .message("템플릿 설명")
                .user("관리자 id")
                .build();
        assertThat(mailTemplate.getContent()).isEqualTo("메일 본문");
        assertThat(mailTemplate.getSubject()).isEqualTo("메일 본문");
        assertThat(mailTemplate.getMessage()).isEqualTo("템플릿 설명");
        assertThat(mailTemplate.getUser()).isEqualTo("관리자 id");
    }

}