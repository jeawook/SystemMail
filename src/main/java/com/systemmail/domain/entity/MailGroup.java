package com.systemmail.domain.entity;

import lombok.*;

import javax.persistence.*;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailGroup {

    @Id @GeneratedValue
    @Column(name = "mail_group_id")
    private Long id;

    private String name;

    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "email"))
    @AttributeOverride(name = "name", column = @Column(name = "emailName"))
    private Email email;

    @Builder
    public MailGroup(String name, Email email) {
        checkArgument(isNotEmpty(name), "이름이 입력되어야 합니다.");
        checkNotNull(email, "이메일주소가 입력되어야 합니다.");
        this.name = name;
        this.email = email;
    }
}
