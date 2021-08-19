package com.systemmail.Service;

import com.systemmail.domain.entity.SendInfo;
import com.systemmail.domain.entity.SendStatus;
import com.systemmail.repository.SendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SendInfoService {

    private final SendInfoRepository sendInfoRepository;

    @Transactional
    public SendInfo saveSendInfo(SendInfo sendInfo) {
        return sendInfoRepository.save(sendInfo);
    }

    public Optional<SendInfo> findSendInfo(Long id) {
        return sendInfoRepository.findById(id);
    }

    public List<SendInfo> findSendList(SendStatus sendStatus,LocalDateTime sendDate) {
        return sendInfoRepository.findBySendStatusAndSendDateLessThanOrderBySendDateDesc(sendStatus, sendDate);
    }
}
