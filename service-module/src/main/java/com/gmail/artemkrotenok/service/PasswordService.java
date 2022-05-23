package com.gmail.artemkrotenok.service;

public interface PasswordService {

    String getNewPassword();

    void sendPasswordToEmail(String password, String email);

}
