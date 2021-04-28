package com.SystemMail.domain;

import lombok.*;

import javax.persistence.*;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailInfo {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column
    private String message;

    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "mailFrom"))
    @AttributeOverride(name = "name", column = @Column(name = "mailFromName"))
    private Email mailFrom;
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "mailTo"))
    @AttributeOverride(name = "name", column = @Column(name = "mailToName"))
    private Email mailTo;
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "replyTo"))
    @AttributeOverride(name = "name", column = @Column(name = "replyToName"))
    private Email replyTo;


    @Builder
    public MailInfo(String message, Email mailFrom, Email mailTo, Email replyTo) {
        checkNotNull(message, "message가 입력되어야 합니다.");
        checkNotNull(mailFrom, "mailFrom이 입력되어야 합니다.");
        checkNotNull(mailTo, "mailTo가 입력되어야 합니다.");
        checkNotNull(replyTo, "replyTo가 입력되어야 합니다.");
        this.message = message;
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.replyTo = replyTo;
    }
}
