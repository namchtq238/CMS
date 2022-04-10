package com.cms.service;

import com.cms.config.dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderCustom {
    @Autowired
    private JavaMailSender mailSender;
    public void sendMail(MailDTO mailTO){
        SimpleMailMessage message = getMail(mailTO);
        mailSender.send(message);
    }

    public SimpleMailMessage getMail(MailDTO dto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getTo());
        message.setFrom(dto.getFrom());
        message.setSubject(dto.getSubject());
        message.setText(dto.getContent());
        return message;
    }
}
