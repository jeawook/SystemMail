package com.SystemMail.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Macro {

    private String macro;

    private String macreData;


    @Builder
    public Macro(String macro, String macreData) {
        int macroSize = macro.split(",").length;
        int valuesSize = macreData.split(",").length;
        if (macroSize != valuesSize) {
            throw new IllegalArgumentException("입력된 매크로 데이터가 일치 하지 않습니다.");
        }
        this.macro = macro;
        this.macreData = macreData;
    }
}
