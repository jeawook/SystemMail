package com.SystemMail.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendInfo {

    @Id @GeneratedValue
    @Column(name = "send_info_id")
    private Long id;

    private SendStatus sendStatus;

    private LocalDateTime sendDate;

    private LocalDateTime completeDate;

    @OneToOne
    @JoinColumn(name = "group_id")
    private MailGroup group;

    @OneToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToOne
    @JoinColumn(name = "mail_info_id")
    private MailInfo mailInfo;

    @OneToOne
    private ResultInfo resultInfo;

    @OneToMany(mappedBy = "sendInfo")
    private List<ResultDetails> resultDetails = new ArrayList<>();


}
