package com.SystemMail.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailInfo {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "mailFrom"))
    private Email mailFrom;
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "mailTo"))
    private Email mailTo;
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "replyTo"))
    private Email replyTo;
    private String header;

    @OneToMany(mappedBy = "mailInfo", cascade = ALL, fetch = LAZY)
    private List<SendInfo> sendInfoList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "template_id")
    private MailTemplate mailTemplate;

    @Builder
    public MailInfo(Email mailFrom, Email mailTo, Email replyTo, String header, List<SendInfo> sendInfoList, MailTemplate mailTemplate) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.replyTo = replyTo;
        this.header = header;
        this.sendInfoList = sendInfoList;
        this.mailTemplate = mailTemplate;
    }

    public void addSendInfo(SendInfo sendInfo) {
        sendInfo.setMailInfo(this);
        getSendInfoList().add(sendInfo);
    }
}
