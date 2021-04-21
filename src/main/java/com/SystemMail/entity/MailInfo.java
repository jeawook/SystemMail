package com.SystemMail.entity;

import com.google.common.base.Preconditions;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailInfo {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column
    private String message;

    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "mailFrom"))
    private Email mailFrom;
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "mailTo"))
    private Email mailTo;
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "replyTo"))
    private Email replyTo;


    @OneToMany(mappedBy = "mailInfo", cascade = ALL, fetch = LAZY)
    private List<SendInfo> sendInfoList = new ArrayList<>();

    @Builder
    public MailInfo(String message, Email mailFrom, Email mailTo, Email replyTo, List<SendInfo> sendInfoList) {
        checkNotNull(message, "message가 입력되어야 합니다.");
        this.message = message;
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.replyTo = replyTo;
        this.sendInfoList = sendInfoList;
    }

    public void addSendInfo(SendInfo sendInfo) {
        sendInfo.setMailInfo(this);
        getSendInfoList().add(sendInfo);
    }
}
