package com.SystemMail.Service;

import com.SystemMail.entity.SendInfo;
import com.SystemMail.repository.SendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SendInfoService {

    private final SendInfoRepository sendInfoRepository;

    public SendInfo createSendInfo(SendInfo sendInfo) {
        return sendInfoRepository.save(sendInfo);
    }

    public Optional<SendInfo> findSendInfo(Long id) {
        return sendInfoRepository.findById(id);
    }
}
