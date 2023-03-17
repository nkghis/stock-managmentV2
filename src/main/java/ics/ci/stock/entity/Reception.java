package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("rec")
public class Reception extends Operation {

    @Column(name = "reference_fournisseur", nullable = true)
    private String operationReferenceFournisseur;

    @Column(name = "disponibilite", nullable = true)
    private Integer dispo;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;

    public Reception(){
        super();
    }

    public Reception(String operation_ref, int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, /*Produit produit,*/ AppUser user, Date operationDateSaisie, String operationReferenceFournisseur, Integer dispo, Fournisseur fournisseur, Ressource ressource) {
        super(operation_ref, operation_qte, operation_date, dispo_operation, projet, /*produit,*/ user, operationDateSaisie);
        this.operationReferenceFournisseur = operationReferenceFournisseur;
        this.dispo = dispo;
        this.fournisseur = fournisseur;
        this.ressource = ressource;
    }

    public String getOperationReferenceFournisseur() {
        return operationReferenceFournisseur;
    }

    public void setOperationReferenceFournisseur(String operationReferenceFournisseur) {
        this.operationReferenceFournisseur = operationReferenceFournisseur;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(Integer dispo) {
        this.dispo = dispo;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }
}
