package com.SystemMail.Service;

import com.SystemMail.domain.entity.MailResultInfo;
import com.SystemMail.repository.ResultInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResultInfoService {
    private final ResultInfoRepository resultInfoRepository;

    @Transactional
    public MailResultInfo saveResultInfo(MailResultInfo mailResultInfo) {
        return resultInfoRepository.save(mailResultInfo);
    }

}
