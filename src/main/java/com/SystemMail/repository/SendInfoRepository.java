package com.SystemMail.repository;

import antlr.collections.List;
import com.SystemMail.domain.entity.SendInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendInfoRepository extends JpaRepository<SendInfo, Long> {

}
