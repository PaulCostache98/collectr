package com.gamification.collectr.service.email;

public interface EmailSender {
    void sendEmail(String toEmail, String subject, String body);
}
