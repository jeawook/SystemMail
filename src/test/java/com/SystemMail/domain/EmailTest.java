package com.SystemMail.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("email 생성 성공 테스트")
    public void createEmailTest() {
        String address = "test@test.com";
        String name = "이름";
        Email email = Email.of(address, name);

        assertThat(email.getDomain()).isEqualTo("test.com");
        assertThat(email.getId()).isEqualTo("test");
        assertThat(email.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("email 생성 오류 테스트")
    public void createEmailErrorTest() {
        String address = "test.com";
        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> {Email.of(address, "");});
        assertThat(throwable.getMessage()).isEqualTo("Invalid email address: "+address);

        String nullAddress = null;
        throwable = assertThrows(IllegalArgumentException.class,
                () -> {Email.of(nullAddress, "");});
        assertThat(throwable.getMessage()).isEqualTo("address must be provided");

        String sizeErrorAddress = "a@d";
        throwable = assertThrows(IllegalArgumentException.class,
                () -> {Email.of(sizeErrorAddress, "");});
        assertThat(throwable.getMessage()).isEqualTo("address length must be between 4 and 50 characters");

    }



}