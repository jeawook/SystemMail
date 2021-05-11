package com.SystemMail.Service;

import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.mail.MailSender;
import com.SystemMail.repository.SendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final SendInfoRepository sendInfoRepository;
    private final MailSender mailSender;


    public void sendMail() {
        List<SendInfo> sendInfoList = sendInfoRepository.findAll();
        mailSender.sender(sendInfoList);
    }

}
