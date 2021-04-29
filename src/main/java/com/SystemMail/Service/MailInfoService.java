package com.SystemMail.Service;

import com.SystemMail.domain.entity.MailInfo;
import com.SystemMail.repository.MailInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailInfoService {

    private final MailInfoRepository mailInfoRepository;

    public Optional<MailInfo> findMailInfoById(Long id) {
        return mailInfoRepository.findById(id);
    }

    public Page<MailInfo> findMailInfo(Pageable pageable) {
        return mailInfoRepository.findAll(pageable);
    }

    @Transactional
    public MailInfo saveMailInfo(MailInfo mailInfo) {
        return mailInfoRepository.save(mailInfo);
    }


}
