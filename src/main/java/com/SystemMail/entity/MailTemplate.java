package com.SystemMail.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailTemplate extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String content;

    private String user;

    private String subject;

    private String message;

    @OneToMany(mappedBy = "mailTemplate")
    private List<MailInfo> mailInfoList = new ArrayList<>();

    @Builder
    public MailTemplate(String content, String user, String subject, String message) {
        checkNotNull(content, "본문이 입력되어야 합니다.");
        checkNotNull(user, "사용자 정보가 입력되어야 합니다..");
        checkNotNull(subject, "제목이 입력되어야 합니다.");
        this.content = content;
        this.user = user;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("content", content)
                .append("user", user)
                .append("subject", subject)
                .append("message", message)
                .toString();
    }
}
