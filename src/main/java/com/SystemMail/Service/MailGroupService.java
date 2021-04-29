package com.SystemMail.Service;

import com.SystemMail.domain.entity.MailGroup;
import com.SystemMail.repository.MailGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailGroupService {
    private final MailGroupRepository mailGroupRepository;

    public MailGroup createMailGroup(MailGroup mailGroup){
        return mailGroupRepository.save(mailGroup);
    }

    public Optional<MailGroup> findMailGroup(Long id) {
        return mailGroupRepository.findById(id);
    }
}
