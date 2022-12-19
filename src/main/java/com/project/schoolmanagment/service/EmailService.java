package com.project.schoolmanagment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class EmailService {

    //@Value("${email.address}")
    private static String MAIL_ADDRESS = "studentmanagmentsys@gmail.com";
    //@Value("${email.password}")
    private static String PASSWORD = "accfjqjcumptmdfc";
    public static void sendMail(String recipient,String mailMessage, String subject) throws MessagingException {

        Properties properties = new Properties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com"); //gmail olacak office365 değil
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.enable", "false");

        System.out.println(MAIL_ADDRESS+" "+PASSWORD);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_ADDRESS, PASSWORD);
            }
        });

        Message message = prepareMessage(session, MAIL_ADDRESS, recipient, mailMessage, subject);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String from, String recipient, String mailMessage, String subject) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(mailMessage);
        return message;
    }
}

