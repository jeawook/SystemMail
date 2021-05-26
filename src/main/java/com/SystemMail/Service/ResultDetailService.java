package com.SystemMail.Service;

import com.SystemMail.domain.entity.MailResultDetail;
import com.SystemMail.repository.ResultDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResultDetailService {

    private final ResultDetailRepository resultDetailRepository;

    @Transactional
    public MailResultDetail saveResultDetail(MailResultDetail mailResultDetail) {
        return resultDetailRepository.save(mailResultDetail);
    }
}
