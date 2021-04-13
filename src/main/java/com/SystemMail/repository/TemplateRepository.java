package com.SystemMail.repository;

import com.SystemMail.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<MailTemplate, Long> {

    List<MailTemplate> findByUser(String user);
}
