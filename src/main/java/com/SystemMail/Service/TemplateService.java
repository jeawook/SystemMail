package com.SystemMail.Service;

import com.SystemMail.entity.MailTemplate;
import com.SystemMail.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Transactional
    public MailTemplate saveMailTemplate(MailTemplate mailTemplate) {
        return templateRepository.save(mailTemplate);
    }

    public Page<MailTemplate> findMailTemplate(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }

    public Optional<MailTemplate> findOne(Long id) {
        return templateRepository.findById(id);
    }


}
