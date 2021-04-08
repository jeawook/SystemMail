package com.SystemMail.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    @Id @GeneratedValue
    @Column(name = "template_id")
    private Long id;

    private String content;

    private String user;

    private String subject;

    @OneToMany(mappedBy = "template")
    private List<MailInfo> mailInfoList = new ArrayList<>();
}
