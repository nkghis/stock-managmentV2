package ics.ci.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationtest() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("ghislain.nkagou@gmail.com");
        mail.setFrom("ghislain.nkagou@gmail.com");
        mail.setSubject("First Notification");
        mail.setText("My first email tesr");
        javaMailSender.send(mail);
        System.out.println("Email send");
    }

    public void sendNotificationStock(SimpleMailMessage simpleMailMessage) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(simpleMailMessage.getTo());
        mail.setFrom(simpleMailMessage.getFrom());
        mail.setSubject(simpleMailMessage.getSubject());
        mail.setText(simpleMailMessage.getText());
        javaMailSender.send(mail);
        System.out.println("Email send");
    }
}
