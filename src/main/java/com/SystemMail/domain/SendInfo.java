package com.SystemMail.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    @ElementCollection
    @CollectionTable(
            name = "send_info_macro",
            joinColumns = @JoinColumn(name = "send_info_id")
    )
    @MapKeyColumn(name = "macro")
    @Column(name = "macroData")
    private HashMap<String, String> macro = new HashMap<>();

    @OneToOne
    @JoinColumn(name = "group_id")
    private MailGroup mailGroup;

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

    public void addResultDetail(ResultDetail resultDetail) {
        getResultDetails().add(resultDetail);
        resultDetail.setSendInfo(this);
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
}
