package com.example.chanel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        // It creates an instance of the JavaMailSenderImpl class,
        // which represents an email sender implementation in the JavaMail API.
        // It allows you to send emails programmatically by configuring the necessary properties
        // and using its methods for sending email messages.
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("sivaranjani262001@gmail.com");
        mailSender.setPassword("wzsqdqyjuxdggdwt");
       //IT retrieves the JavaMail-specific properties associated with the JavaMailSenderImpl object.
        // These properties can be accessed and modified through the props variable,
        // allowing further customization of the email sender's behavior,
        // such as SMTP settings, timeouts, and debugging options.
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // It sets the "mail.debug" property to "true" in the Properties object props.
        // This enables debugging mode for email operations,
        // providing detailed information about the email sending process for troubleshooting and logging purposes.
        props.put("mail.debug", "true");

        return mailSender;
    }
}
