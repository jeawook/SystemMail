package com.SystemMail.Service;

import com.SystemMail.domain.entity.MailTemplate;
import com.SystemMail.repository.MailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {

    private final MailTemplateRepository templateRepository;

    @Transactional
    public MailTemplate saveMailTemplate(MailTemplate mailTemplate) {
        return templateRepository.save(mailTemplate);
    }

    public Page<MailTemplate> findMailTemplate(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }

    public Optional<MailTemplate> findMailTemplateById(Long id) {
        return templateRepository.findById(id);
    }


}
