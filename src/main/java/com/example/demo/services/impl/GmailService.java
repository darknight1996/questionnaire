package com.example.demo.services.impl;

import com.example.demo.models.User;
import com.example.demo.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailService implements MailService {

    @Autowired
    @Qualifier("javaMailSenderBean")
    public JavaMailSender emailSender;

    private void sendMessage(String email,String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        // Send Message
        this.emailSender.send(message);
    }

    @Override
    public void sendRegistrationNotification(User user) {
        sendMessage(
                user.getUsername(),
                "Welcome to Questionnaire",
                "Hello, " + user.getFirstName() + ", You have been registered on Questionnaire Portal!"
        );
    }

    @Override
    public void changePasswordNotification(User user) {
        sendMessage(
                user.getUsername(),
                "Questionnaire",
                "Hello, " + user.getFirstName() + ", Your password has been changed."
        );
    }
}
