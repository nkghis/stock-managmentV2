package ics.ci.stock.entity;


import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "v_operation")
public class Voperation {

    @Id
    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "client")
    private String client;

    @Column(name = "projet")
    private String projet;

    @Column(name = "produit")
    private String produit;

    @Column(name = "emetteur")
    private String emetteur;

    @Column(name = "quantite")
    private  Integer quantite;

    @Column(name = "initial")
    private  Integer initial;

    @Column(name = "final")
    private  Integer finale;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "operation")
    private String operation;

    @Column(name = "gache")
    private  Integer gache;

    @Column(name = "retour")
    private  Integer retour;

    @Column(name = "entrepot")
    private  String entrepot;

    @Column(name = "projet_id")
    private Long projetId;

    @Column(name = "operation_date_saisie", nullable = true)
    private Date operationDateSaisie;


    public Voperation() {
        super();
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(String emetteur) {
        this.emetteur = emetteur;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getInitial() {
        return initial;
    }

    public void setInitial(Integer initial) {
        this.initial = initial;
    }

    public Integer getFinale() {
        return finale;
    }

    public void setFinale(Integer finale) {
        this.finale = finale;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getGache() {
        return gache;
    }

    public void setGache(Integer gache) {
        this.gache = gache;
    }

    public Integer getRetour() {
        return retour;
    }

    public void setRetour(Integer retour) {
        this.retour = retour;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public Long getProjetId() {
        return projetId;
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    public Date getOperationDateSaisie() {
        return operationDateSaisie;
    }

    public void setOperationDateSaisie(Date operationDateSaisie) {
        this.operationDateSaisie = operationDateSaisie;
    }
}
