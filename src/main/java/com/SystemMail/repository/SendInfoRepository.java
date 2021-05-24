package com.SystemMail.repository;

import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.domain.entity.SendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SendInfoRepository extends JpaRepository<SendInfo, Long> {

    List<SendInfo> findBySendStatusAndSendDateLessThanOrderBySendDateDesc(SendStatus sendStatus, LocalDateTime sendDate);
}
