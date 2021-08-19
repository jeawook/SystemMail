package com.systemmail.repository;

import com.systemmail.domain.entity.MailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailInfoRepository extends JpaRepository<MailInfo, Long> {

}
