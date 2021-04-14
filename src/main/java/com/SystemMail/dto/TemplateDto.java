package com.SystemMail.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Builder
@Getter
@Setter
public class TemplateDto {
    private String content;
    private String subject;
    private String message;
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("content", content)
                .append("subject", subject)
                .append("message", message)
                .toString();
    }
}
