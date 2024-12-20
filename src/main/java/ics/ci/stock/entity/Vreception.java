package ics.ci.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "v_reception")
public class Vreception {

    @Id
    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "typeoperation")
    private String typeOperation;

    @Column(name = "dispo_operation")
    private boolean dispoOperation;

    @Column(name = "operation_qte")
    private int qteOperation;

    @Column(name = "operation_reference")
    private String operationReference;

    @Column(name = "reference_fournisseur")
    private String referenceFournisseur;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    @Column(name = "disponibilite")
    private int disponibilite;

    @Column(name = "provider_nom")
    private String fournisseurNom;

   /* @Column(name = "produit_nom")
    private String produitNom;*/

    @Column(name = "ressource_nom")
    private String ressourceNom;

    @Column(name = "projet_nom")
    private String projetNom;

    @Column(name = "client_nom")
    private String clientNom;

    @Column(name = "produit_nom")
    private String produitNom;

    @Column(name = "emetteur_nom")
    private String emetteurNom;

    @Column(name = "provider_nom", insertable = false, updatable = false)
    private String providerNom;

    @Column(name = "operation_date_saisie", nullable = true)
    private Date operationDateSaisie;


    public Vreception() {
        super();
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public boolean isDispoOperation() {
        return dispoOperation;
    }

    public void setDispoOperation(boolean dispoOperation) {
        this.dispoOperation = dispoOperation;
    }

    public int getQteOperation() {
        return qteOperation;
    }

    public void setQteOperation(int qteOperation) {
        this.qteOperation = qteOperation;
    }

    public String getOperationReference() {
        return operationReference;
    }

    public void setOperationReference(String operationReference) {
        this.operationReference = operationReference;
    }

    public String getReferenceFournisseur() {
        return referenceFournisseur;
    }

    public void setReferenceFournisseur(String referenceFournisseur) {
        this.referenceFournisseur = referenceFournisseur;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public int getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(int disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getFournisseurNom() {
        return fournisseurNom;
    }

    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }

  /*  public String getProduitNom() {
        return produitNom;
    }

    public void setProduitNom(String produitNom) {
        this.produitNom = produitNom;
    }*/

    public String getRessourceNom() {
        return ressourceNom;
    }

    public void setRessourceNom(String ressourceNom) {
        this.ressourceNom = ressourceNom;
    }

    public String getProjetNom() {
        return projetNom;
    }

    public void setProjetNom(String projetNom) {
        this.projetNom = projetNom;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public void setProduitNom(String produitNom) {
        this.produitNom = produitNom;
    }

    public String getEmetteurNom() {
        return emetteurNom;
    }

    public void setEmetteurNom(String emetteurNom) {
        this.emetteurNom = emetteurNom;
    }

    public Date getOperationDateSaisie() {
        return operationDateSaisie;
    }

    public void setOperationDateSaisie(Date operationDateSaisie) {
        this.operationDateSaisie = operationDateSaisie;
    }

    public String getProviderNom() {
        return providerNom;
    }

    public void setProviderNom(String providerNom) {
        this.providerNom = providerNom;
    }
}
