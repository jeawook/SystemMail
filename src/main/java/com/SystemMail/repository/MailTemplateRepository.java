package com.SystemMail.repository;

import com.SystemMail.entity.MailTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {

}
