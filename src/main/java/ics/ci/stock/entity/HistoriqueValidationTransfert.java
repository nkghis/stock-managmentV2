package ics.ci.stock.entity;

import ics.ci.stock.enums.Statut;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_validation_transfert")
public class HistoriqueValidationTransfert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "commentaires", nullable = true)
    private String commentaires;


    @ManyToOne
    @JoinColumn(name = "validationtransfertId")
    private ValidationTransfert validationTransfert;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;


    @Column(name = "statut")
    private Statut statut;

    public HistoriqueValidationTransfert() {
        super();
    }

    public HistoriqueValidationTransfert(LocalDateTime date, String commentaires, ValidationTransfert validationTransfert, AppUser user, Statut statut) {
        this.date = date;
        this.commentaires = commentaires;
        this.validationTransfert = validationTransfert;
        this.user = user;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public ValidationTransfert getValidationTransfert() {
        return validationTransfert;
    }

    public void setValidationTransfert(ValidationTransfert validationTransfert) {
        this.validationTransfert = validationTransfert;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
