package com.SystemMail.repository;

import com.SystemMail.domain.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

}
