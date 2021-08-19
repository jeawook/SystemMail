package com.systemmail.common;

import com.systemmail.domain.entity.MailTemplate;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MailTemplateSerializer extends JsonSerializer<MailTemplate> {
    @Override
    public void serialize(MailTemplate mailTemplate, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", mailTemplate.getId());
        gen.writeStringField("user", mailTemplate.getUser());
        gen.writeStringField("subject", mailTemplate.getSubject());
        gen.writeStringField("message", mailTemplate.getMessage());
        gen.writeStringField("content", mailTemplate.getContent());
        gen.writeEndObject();
    }
}
