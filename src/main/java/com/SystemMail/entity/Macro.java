package com.SystemMail.entity;

import com.sun.mail.iap.Argument;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Macro {

    private String macro;

    private String values;


    @Builder
    public Macro(String macro, String values) {
        int macroSize = macro.split(",").length;
        int valuesSize = values.split(",").length;
        if (macroSize != valuesSize) {
            throw new IllegalArgumentException("입력된 매크로 데이터가 일치 하지 않습니다.");
        }
        this.macro = macro;
        this.values = values;
    }
}
