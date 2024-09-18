package com.SlicK.basic_crud.Utils;

import com.SlicK.basic_crud.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
//@ConditionalOnProperty()
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Value("${email.job.cron}")
    private String cronExpression;

    @Scheduled(cron = "${email.job.cron}")
    public void sendScheduledEmail() {
        String toEmail = "qorenagib@gotgel.org";
        String subject = "Scheduled Email";
        String body = "This is an automated email sent every hour.";

        emailService.sendSimpleEmail(toEmail, subject, body);
        System.out.println("Scheduled email sent.");
    }
}