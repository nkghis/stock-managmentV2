package ics.ci.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    private final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String toEmail, String subject, String message/*, String from*/){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("notification.stockmanagement@ics.ci");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        //String from = "NOTIFICATION PERSO";
        //mailMessage.setFrom("");
       /* mailMessage.setFrom(new InternetAddress("Sender Name" + "<" + "no-reply@domain.com" + ">"));*/
       /* mailMessage.setFrom(from);*/

        javaMailSender.send(mailMessage);
        System.out.println("Mail envoyé avec succès");
    }


/*    public void sendNotificationtest() {
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
    }*/



}
