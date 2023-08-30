package ics.ci.stock.service;

import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Notification;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.repository.NotificationRepository;
import ics.ci.stock.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {


    private final JavaMailSender javaMailSender;

    private final NotificationRepository notificationRepository;

    private final ConfigProperties configProperties;

    public NotificationService(JavaMailSender javaMailSender, NotificationRepository notificationRepository, ConfigProperties configProperties) {
        this.javaMailSender = javaMailSender;
        this.notificationRepository = notificationRepository;
        this.configProperties = configProperties;
    }



  /*  @Scheduled(fixedDelay = 5000)*/
    @Async
    public void sendEmail( String subject, String message,  AppUser user, Projet projet) throws InterruptedException{

        System.out.println("Sleeping now...");
        //Delai de 5 seconde avant execution du code ci-dessous
        Thread.sleep(5000);

        System.out.println("Sending email...");

/*        String from = "notification.stockmanagement@ics.ci";
        String to = "ghislain.nkagou@ics.ci";*/

        String from = configProperties.getConfigValue("stock.managment.email.etatstock.from");
        String to = configProperties.getConfigValue("stock.managment.email.etatstock.to");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        System.out.println("Mail envoyé avec succès");

        Notification notification = new Notification();
        notification.setDateTime(LocalDateTime.now());
        notification.setA(to);
        notification.setSujet(subject);
        notification.setMessage(message);
        notification.setProjet(projet);
        notification.setUser(user);
        this.addNotification(notification);
        System.out.println("Notification ajoutée avec succès");
    }

    public void addNotification(Notification notification){
        notificationRepository.save(notification);
    }

}
