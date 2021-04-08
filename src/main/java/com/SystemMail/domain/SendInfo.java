package com.SystemMail.domain;

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
    @Column(name = "send_info_id")
    private Long id;

    private SendStatus sendStatus;

    private LocalDateTime sendDate;

    private LocalDateTime completeDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private MailGroup group;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "mail_info_id")
    private MailInfo mailInfo;

    @OneToOne
    @JoinColumn(name = "result_info_id")
    private ResultInfo resultInfo;

    @OneToMany(mappedBy = "sendInfo", fetch = LAZY, cascade = ALL)
    private List<ResultDetail> resultDetails = new ArrayList<>();

    @Builder
    public SendInfo(@NonNull LocalDateTime sendDate, MailGroup group, Template template, MailInfo mailInfo) {

        this.sendDate = sendDate;
        this.group = group;
        this.template = template;
        this.mailInfo = mailInfo;
    }

    public void addResultDetail(ResultDetail resultDetail) {
        getResultDetails().add(resultDetail);
        resultDetail.setSendInfo(this);
    }


}
