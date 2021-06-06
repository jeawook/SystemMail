package com.SystemMail.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendInfo {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SendStatus sendStatus = SendStatus.READY;

    private LocalDateTime sendDate;

    private LocalDateTime completeDate;

    @ElementCollection
    @CollectionTable(
            name = "send_info_macro",
            joinColumns = @JoinColumn(name = "id")
    )
    @MapKeyColumn(name = "macroValue")
    @Column(name = "macroData")
    private Map<String, String> macro = new HashMap<>();

    @OneToOne
    @JoinColumn(name = "group_id")
    private MailGroup mailGroup;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private MailTemplate mailTemplate;

    @ManyToOne
    @JoinColumn(name = "mail_info_id")
    private MailInfo mailInfo;

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "result_info_id" )
    private MailResultInfo mailResultInfo;

    @OneToMany(mappedBy = "sendInfo", cascade = ALL, fetch = LAZY)
    private List<MailResultDetail> mailResultDetails = new ArrayList<>();

    @Builder
    public SendInfo(LocalDateTime sendDate, MailGroup mailGroup, MailTemplate mailTemplate, MailInfo mailInfo) {
        checkNotNull(mailGroup, "그룹정보가 입력 되지 않았습니다.");
        checkNotNull(mailTemplate, "템플릿 정보가 입력 되지 않았습니다.");
        checkNotNull(mailInfo, "발송자 정보가 입력되지 않았습니다.");

        if (sendDate == null) {
            sendDate = LocalDateTime.now();
        }
        this.sendDate = sendDate;
        this.mailGroup = mailGroup;
        this.mailTemplate = mailTemplate;
        this.mailInfo = mailInfo;
    }

    public void addResultDetail(MailResultDetail mailResultDetail) {
        getMailResultDetails().add(mailResultDetail);
        mailResultDetail.setSendInfo(this);
    }
    public void setMailResultInfo(MailResultInfo mailResultInfo) {
        this.mailResultInfo = mailResultInfo;
        mailResultInfo.setSendInfo(this);
    }

    public void setMailInfo(MailInfo mailInfo) {
        this.mailInfo = mailInfo;
    }

    public void setMacro(String[] macroValue, String[] macroData) {
        checkArgument(macroValue.length == macroData.length, "macro 데이터가 잘못 입력되었습니다.");
        for (int i=0; i < macroValue.length; i++) {
            macro.put(macroValue[i], macroData[i]);
        }
    }

    public String makeContent() {
        checkNotNull(mailTemplate.getContent(), "content 가 입력 되지 않았습니다.");
        String content = mailTemplate.getContent();
        for (String key : macro.keySet()) {
            content = content.replaceAll("\\[\\$" + key + "\\$\\]", macro.get(key));
        }
        return content;
    }

    public void sending() {
        if (this.getSendStatus() == SendStatus.SENDING || this.getSendStatus() == SendStatus.COMPLETE) {
            return;
        }
        this.sendStatus = SendStatus.SENDING;
    }

    public void error() {
        this.sendStatus = SendStatus.ERROR;
    }

    public void setComplete() {
        this.sendStatus = SendStatus.COMPLETE;
        this.completeDate = LocalDateTime.now();
    }


}
