package com.SystemMail.repository;

import com.SystemMail.domain.SendInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendInfoRepository extends JpaRepository<SendInfo, Long> {

}
