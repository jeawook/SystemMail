package com.SystemMail.Service;

import com.SystemMail.entity.MailTemplate;
import com.SystemMail.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Transactional
    public MailTemplate createTemplate(MailTemplate mailTemplate) {
        return templateRepository.save(mailTemplate);
    }

    public List<MailTemplate> findTemplates(String user) {
        return templateRepository.findByUser(user);
    }

    public Optional<MailTemplate> findOne(Long id) {
        return templateRepository.findById(id);
    }


}
