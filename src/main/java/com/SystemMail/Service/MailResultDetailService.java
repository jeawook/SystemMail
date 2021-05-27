package com.SystemMail.Service;

import com.SystemMail.domain.entity.MailResultDetail;
import com.SystemMail.repository.MailResultDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailResultDetailService {

    private final MailResultDetailRepository mailResultDetailRepository;

    @Transactional
    public MailResultDetail saveResultDetail(MailResultDetail mailResultDetail) {
        return mailResultDetailRepository.save(mailResultDetail);
    }
}
