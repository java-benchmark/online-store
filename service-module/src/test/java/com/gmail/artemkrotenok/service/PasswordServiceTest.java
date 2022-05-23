package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.service.impl.PasswordServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    private PasswordService passwordService;

    @BeforeEach
    public void setup() {
        this.passwordService = new PasswordServiceImpl(javaMailSender);
    }

    @Test
    public void getNewPassword_returnNotNull() {
        String password = passwordService.getNewPassword();
        Assertions.assertThat(password).isNotNull();
    }

}
