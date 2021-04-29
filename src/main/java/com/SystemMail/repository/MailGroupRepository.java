package com.SystemMail.repository;

import com.SystemMail.domain.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailGroupRepository extends JpaRepository<MailGroup, Long> {
}
