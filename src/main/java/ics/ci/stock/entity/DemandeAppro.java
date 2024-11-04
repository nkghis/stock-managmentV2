package ics.ci.stock.entity;


import ics.ci.stock.enums.Statut;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "demamade_appro")
public class DemandeAppro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "reference", length = 10, unique = true)
    private String reference;

    @Column(name = "statut")
    private Statut statut;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "entrepotIdDestination")
    private Entrepot entrepotDestination;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    public DemandeAppro() {
        super();
    }

    public DemandeAppro(String reference, Statut statut, LocalDateTime date, Entrepot entrepotDestination, AppUser user) {
        this.reference = reference;
        this.statut = statut;
        this.date = date;
        this.entrepotDestination = entrepotDestination;
        this.user = user;
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

    public Entrepot getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(Entrepot entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
