package com.systemmail.Service;

import com.systemmail.domain.entity.MailResultInfo;
import com.systemmail.repository.MailResultInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailResultInfoService {
    private final MailResultInfoRepository mailResultInfoRepository;

    @Transactional
    public MailResultInfo saveResultInfo(MailResultInfo mailResultInfo) {
        return mailResultInfoRepository.save(mailResultInfo);
    }

}
