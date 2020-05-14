package ics.ci.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "v_enlevement_retour")
public class VenlevementRetour {

    @Id
    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "typeoperation")
    private String typeOperation;

    @Column(name = "operation_qte")
    private int qteOperation;

    @Column(name = "operation_reference")
    private String operationReference;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    /*@Column(name = "produit_nom")
    private String produitNom;*/

    @Column(name = "ressource_nom")
    private String ressourceNom;

    @Column(name = "projet_nom")
    private String projetNom;

    @Column(name = "motifs")
    private String motifNom;

    public VenlevementRetour() {
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

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

   /* public String getProduitNom() {
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

    public String getMotifNom() {
        return motifNom;
    }

    public void setMotifNom(String motifNom) {
        this.motifNom = motifNom;
    }
}
