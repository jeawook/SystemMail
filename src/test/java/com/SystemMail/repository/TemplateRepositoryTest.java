package com.SystemMail.repository;

import com.SystemMail.entity.MailTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TemplateRepositoryTest {

    @Autowired
    TemplateRepository templateRepository;
    @Test
    public void pagingTest() {
        String user = "사용자1";
        String content = "메일 본문";
        String subject = "메일 제목";
        String message = "발송용";
        templateRepository.save(MailTemplate.builder().user(user).content(content).subject(subject).message(message).build());
        templateRepository.save(MailTemplate.builder().user(user).content(content).subject(subject).message(message).build());
        templateRepository.save(MailTemplate.builder().user(user).content(content).subject(subject).message(message).build());

        PageRequest pageRequest = PageRequest.of(1, 2);
        Page<MailTemplate> page = templateRepository.findAll(pageRequest);


        List<MailTemplate> mailTemplates = page.getContent();

        for (MailTemplate mailTemplate : mailTemplates) {
            System.out.println(mailTemplate.getId());
            System.out.println(mailTemplate.getSubject());
            System.out.println(mailTemplate.getMessage());
            System.out.println(mailTemplate.getContent());
        }

        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getNumber()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(3);


    }

}