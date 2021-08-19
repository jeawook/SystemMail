package com.systemmail.repository;

import com.systemmail.domain.entity.MailGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailGroupRepository extends JpaRepository<MailGroup, Long> {
}
