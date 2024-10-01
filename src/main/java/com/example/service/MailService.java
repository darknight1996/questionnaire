package com.example.service;

import com.example.models.User;

public interface MailService {

    void sendRegistrationNotification(User user);

    void changePasswordNotification(User user);
}
