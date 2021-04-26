package com.SystemMail.entity;

import com.google.common.base.Preconditions;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private SendStatus sendStatus;

    private LocalDateTime sendDate;

    private LocalDateTime completeDate;

    private Macro macro;

    @OneToOne
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
    public SendInfo(LocalDateTime sendDate, MailGroup group, MailTemplate mailTemplate, MailInfo mailInfo, Macro macro) {
        checkNotNull(group, "그룹정보가 입력 되지 않았습니다.");
        checkNotNull(mailTemplate, "템플릿 정보가 입력 되지 않았습니다.");
        checkNotNull(mailInfo, "발송자 정보가 입력되지 않았습니다.");
        if (sendDate == null) {
            sendDate = LocalDateTime.now();
        }
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
