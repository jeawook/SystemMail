package com.SystemMail.repository;

import com.SystemMail.domain.entity.MailResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultDetailRepository extends JpaRepository<MailResultDetail, Long> {
}
