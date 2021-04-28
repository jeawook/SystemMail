package com.SystemMail.domain;

import lombok.*;

import javax.persistence.*;

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
}
