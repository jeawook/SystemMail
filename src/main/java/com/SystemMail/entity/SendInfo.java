package com.SystemMail.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private SendStatus sendStatus;

    private LocalDateTime sendDate;

    private LocalDateTime completeDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private MailGroup group;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private MailTemplate mailTemplate;

    @ManyToOne
    @JoinColumn(name = "mail_info_id")
    private MailInfo mailInfo;

    @OneToOne
    @JoinColumn(name = "result_info_id")
    private ResultInfo resultInfo;

    @OneToMany(mappedBy = "sendInfo", fetch = LAZY, cascade = ALL)
    private List<ResultDetail> resultDetails = new ArrayList<>();

    @Builder
    public SendInfo(LocalDateTime sendDate, MailGroup group, MailTemplate mailTemplate, MailInfo mailInfo) {
        this.sendDate = sendDate;
        this.group = group;
        this.mailTemplate = mailTemplate;
        this.mailInfo = mailInfo;
    }

    public void addResultDetail(ResultDetail resultDetail) {
        getResultDetails().add(resultDetail);
        resultDetail.setSendInfo(this);
    }

    public void setMailInfo(MailInfo mailInfo) {
        this.mailInfo = mailInfo;
    }
}
