package ics.ci.stock.service;

import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Notification;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.repository.NotificationRepository;
import ics.ci.stock.utils.ConfigProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

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
    public void sendEmailStock (String subject, String message, AppUser user, Projet projet) throws InterruptedException{

        //Don't forget to add annotation "@EnableAsync"  in file "StockManagmentApplication.java" under "@SpringBootApplication"

        System.out.println("Sleeping now...");
        //Délai de 5 secondes avant execution du code ci-dessous
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

    @Async
    public void sendEmailValidation(String subject, String message, AppUser user, Projet projet, String[] to) throws InterruptedException {

        //Don't forget to add annotation "@EnableAsync"  in file "StockManagmentApplication.java" under "@SpringBootApplication"

        System.out.println("Sleeping now Demande...");
        //Délai de 5 secondes avant execution du code ci-dessous
        Thread.sleep(5000);

        System.out.println("Demande de validation Transfert  - Sending email...");


        String from = configProperties.getConfigValue("stock.managment.email.etatstock.from");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        System.out.println("Notification - Demande de validation Transfert  envoyé avec succès");

        Notification notification = new Notification();
        notification.setDateTime(LocalDateTime.now());
        notification.setA(Arrays.toString(to));
        notification.setSujet(subject);
        notification.setMessage(message);
        notification.setProjet(projet);
        notification.setUser(user);
        this.addNotification(notification);
        System.out.println("Notification Demande ajoutée avec succès");
    }

    @Async
    public void sendEmailValidation(String subject, String message, AppUser user, Projet projet, String to) throws InterruptedException {

        //Don't forget to add annotation "@EnableAsync"  in file "StockManagmentApplication.java" under "@SpringBootApplication"

        System.out.println("Sleeping now Validation...");
        //Délai de 5 secondes avant execution du code ci-dessous
        Thread.sleep(5000);

        System.out.println("Sending email Validation...");


        String from = configProperties.getConfigValue("stock.managment.email.etatstock.from");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        System.out.println("Mail Validation transfert envoyé avec succès");

        Notification notification = new Notification();
        notification.setDateTime(LocalDateTime.now());
        notification.setA(to);
        notification.setSujet(subject);
        notification.setMessage(message);
        notification.setProjet(projet);
        notification.setUser(user);
        this.addNotification(notification);
        System.out.println("Notification Validation ajoutée avec succès");
    }

}
