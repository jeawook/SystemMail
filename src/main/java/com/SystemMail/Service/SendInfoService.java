package com.SystemMail.Service;

import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.domain.entity.SendStatus;
import com.SystemMail.repository.SendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SendInfoService {

    private final SendInfoRepository sendInfoRepository;

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
