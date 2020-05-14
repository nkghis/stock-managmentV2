package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("enl")
public class Enlevement extends Operation  {

    /*@Column(name = "reference", length = 36, nullable = true)*/
    //private Integer estlivre;
    @Column(name = "enlevement_disponibilite", nullable = true)
    private Integer enlevementDispo;

    @Column(name = "est_gache", nullable = true)
    private Boolean estGache;

    @Column(name = "est_retour", nullable = true)
    private Boolean estRetour;

    @Column(name = "gache", nullable = true)
    private Integer gache;

    @Column(name = "stock_initial")
    private int stockInitial;

    @Column(name = "stock_final")
    private int stockFinal;


    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;

    @ManyToOne
    @JoinColumn(name = "motif_id")
    private Motif motif;

    @ManyToOne
    @JoinColumn(name = "entreposer_id")
    private Entreposer entreposer;

    @ManyToOne
    @JoinColumn(name = "entrepotId")
    private Entrepot entrepot;

    public Enlevement(){
        super();
    }

    public Enlevement(String operation_ref, int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, AppUser user, Integer enlevementDispo, Boolean estGache, Boolean estRetour, Integer gache, int stockInitial, int stockFinal, Ressource ressource, Motif motif, Entreposer entreposer, Entrepot entrepot) {
        super(operation_ref, operation_qte, operation_date, dispo_operation, projet, user);
        this.enlevementDispo = enlevementDispo;
        this.estGache = estGache;
        this.estRetour = estRetour;
        this.gache = gache;
        this.stockInitial = stockInitial;
        this.stockFinal = stockFinal;
        this.ressource = ressource;
        this.motif = motif;
        this.entreposer = entreposer;
        this.entrepot = entrepot;
    }


    public Integer getEnlevementDispo() {
        return enlevementDispo;
    }

    public void setEnlevementDispo(Integer enlevementDispo) {
        this.enlevementDispo = enlevementDispo;
    }

    public Boolean getEstGache() {
        return estGache;
    }

    public void setEstGache(Boolean estGache) {
        this.estGache = estGache;
    }

    public Boolean getEstRetour() {
        return estRetour;
    }

    public void setEstRetour(Boolean estRetour) {
        this.estRetour = estRetour;
    }

    public Integer getGache() {
        return gache;
    }

    public void setGache(Integer gache) {
        this.gache = gache;
    }

    public int getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(int stockInitial) {
        this.stockInitial = stockInitial;
    }

    public int getStockFinal() {
        return stockFinal;
    }

    public void setStockFinal(int stockFinal) {
        this.stockFinal = stockFinal;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public Entreposer getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Entreposer entreposer) {
        this.entreposer = entreposer;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
}
