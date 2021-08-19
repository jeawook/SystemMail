package com.systemmail.Service;

import com.systemmail.domain.entity.MailGroup;
import com.systemmail.repository.MailGroupRepository;
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
