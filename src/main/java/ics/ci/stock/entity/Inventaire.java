package ics.ci.stock.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "inventaires")
public class Inventaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventaireId;


    private String libelle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_inventaire", nullable = true)
    private LocalDateTime dateInventaire;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_debut", nullable = true)
    private Date dateDebut;



    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_fin", nullable = true)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "entrepotId")
    private Entrepot entrepot;

    public Inventaire() {
        super();
    }

    public Inventaire(Long inventaireId, String libelle, Date dateDebut, Date dateFin, Entrepot entrepot, LocalDateTime dateInventaire) {
        this.inventaireId = inventaireId;
        this.libelle = libelle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.entrepot = entrepot;
        this.dateInventaire = dateInventaire;
    }

    public Long getInventaireId() {
        return inventaireId;
    }

    public void setInventaireId(Long inventaireId) {
        this.inventaireId = inventaireId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public LocalDateTime getDateInventaire() {
        return dateInventaire;
    }

    public void setDateInventaire(LocalDateTime dateInventaire) {
        this.dateInventaire = dateInventaire;
    }
}
