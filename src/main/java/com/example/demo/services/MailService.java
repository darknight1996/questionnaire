package com.example.demo.services;

import com.example.demo.models.User;

public interface MailService {

    void sendRegistrationNotification(User user);

    void changePasswordNotification(User user);
}
