package com.SystemMail.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
public class SendInfo {

    @Id @GeneratedValue
    @Column(name = "send_info_id")
    private Long id;

    private SendStatus sendStatus;

    private LocalDateTime sendDate;

    private LocalDateTime completeDate;

    private Group group;

    private Template template;

    private MailInfo mailInfo;

    @OneToOne
    private ResultInfo resultInfo;

    @OneToMany
    private List<ResultDetails> resultDetails = new ArrayList<>();


}
