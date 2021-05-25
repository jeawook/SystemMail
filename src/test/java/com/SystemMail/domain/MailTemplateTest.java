package com.SystemMail.domain;

import com.SystemMail.domain.entity.MailTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailTemplateTest {

    @Test
    @DisplayName("템플릿 생성 테스트")
    public void createMailTemplate() {
        MailTemplate mailTemplate = MailTemplate.builder()
                .content("메일 본문")
                .subject("메일 제목")
                .message("템플릿 설명")
                .user("관리자 id")
                .build();
        assertThat(mailTemplate.getContent()).isEqualTo("메일 본문");
        assertThat(mailTemplate.getSubject()).isEqualTo("메일 제목");
        assertThat(mailTemplate.getMessage()).isEqualTo("템플릿 설명");
        assertThat(mailTemplate.getUser()).isEqualTo("관리자 id");
    }

}