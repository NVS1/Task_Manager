package ua.kiev.prog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Sender implements Runnable {
    @Autowired
    private JavaMailSender javaMailSender;

    private String to;
    private String message;

    public Sender(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void run() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Task");
        mailMessage.setTo(to);
        mailMessage.setFrom(username);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }
}
