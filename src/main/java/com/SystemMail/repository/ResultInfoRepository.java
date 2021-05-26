package com.SystemMail.repository;

import com.SystemMail.domain.entity.MailResultInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultInfoRepository extends JpaRepository<MailResultInfo, Long> {
}
