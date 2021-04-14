package com.SystemMail.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("email 생성 성공 테스트")
    public void createEmailTest() {
        String address = "test@test.com";
        Email email = Email.of(address);

        assertThat(email.equals(address));
        assertThat(email.getDomain().equals("test.com"));
        assertThat(email.getName().equals("test"));
        assertThat(email.getAddress().equals(address));
    }

    @Test
    @DisplayName("email 생성 오류 테스트")
    public void createEmailErrorTest() {
        String address = "test.com";
        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> {Email.of(address);});
        assertThat(throwable.getMessage().equals("Invalid email address:"));

        String nullAddress = null;
        throwable = assertThrows(IllegalArgumentException.class,
                () -> {Email.of(nullAddress);});
        assertThat(throwable.getMessage().equals("address must be provided"));

        String sizeErrorAddress = "a@d";
        throwable = assertThrows(IllegalArgumentException.class,
                () -> {Email.of(sizeErrorAddress);});
        assertThat(throwable.getMessage().equals("address length must be between 4 and 50 characters"));

    }



}