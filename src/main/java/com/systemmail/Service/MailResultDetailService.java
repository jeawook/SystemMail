package com.systemmail.Service;

import com.systemmail.domain.entity.MailResultDetail;
import com.systemmail.repository.MailResultDetailRepository;
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
