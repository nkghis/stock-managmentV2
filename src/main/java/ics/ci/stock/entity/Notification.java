package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private LocalDateTime dateTime;
    private String a;
    private String sujet ;

    @Lob
    private String message ;


    @ManyToOne
    @JoinColumn(name = "projetId")
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    public Notification() {
        super();
    }

    public Notification(Long notificationId, LocalDateTime dateTime, String a, String sujet, String message, Projet projet, AppUser user) {
        this.notificationId = notificationId;
        this.dateTime = dateTime;
        this.a = a;
        this.sujet = sujet;
        this.message = message;
        this.projet = projet;
        this.user = user;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
