package com.SystemMail.Service;

import com.SystemMail.domain.entity.ResultDetail;
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
    public ResultDetail saveResultDetail(ResultDetail resultDetail) {
        return resultDetailRepository.save(resultDetail);
    }
}
