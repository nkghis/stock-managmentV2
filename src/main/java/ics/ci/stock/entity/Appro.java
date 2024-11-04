package ics.ci.stock.entity;

import ics.ci.stock.enums.Etat;
import ics.ci.stock.enums.Statut;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appro")
public class Appro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "qte", nullable = false)
    private int qte;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "etat")
    private Etat etat;

    @ManyToOne
    @JoinColumn(name = "entreposer_id")
    private Entreposer entreposer;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "detailDemandeApproId")
    private DetailDemandeAppro detailDemandeAppro;

    public Appro() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Entreposer getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Entreposer entreposer) {
        this.entreposer = entreposer;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public DetailDemandeAppro getDetailDemandeAppro() {
        return detailDemandeAppro;
    }

    public void setDetailDemandeAppro(DetailDemandeAppro detailDemandeAppro) {
        this.detailDemandeAppro = detailDemandeAppro;
    }
}
