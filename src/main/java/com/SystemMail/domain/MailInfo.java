package com.SystemMail.domain;

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
    @Column(name = "mail_info_id")
    private Long id;

    private String mailFrom;
    private String mailTo;
    private String replyTo;
    private String header;

    @OneToMany(mappedBy = "mailInfo", cascade = ALL, fetch = LAZY)
    private List<SendInfo> sendInfoList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Builder
    public MailInfo(Long id, String mailFrom, String mailTo, String replyTo, String header, List<SendInfo> sendInfoList, Template template) {
        this.id = id;
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.replyTo = replyTo;
        this.header = header;
        this.sendInfoList = sendInfoList;
        this.template = template;
    }

    public void addSendInfo(SendInfo sendInfo) {
        sendInfo.setMailInfo(this);
        getSendInfoList().add(sendInfo);
    }
}
