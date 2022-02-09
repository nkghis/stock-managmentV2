package ics.ci.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/*@Entity
@Table(name = "v_entreposage_true")*/
public class Ventreposage {

    @Id
    @Column(name = "operation_id")
    private Long entreposageId;

    @Column(name = "operation_reference")
    private String entreposageReference;


    @Column(name = "operation_qte")
    private int operationQte;

/*    @Column(name = "projet_nom")
    private String projetNom;*/

    //Add for manage transfert
    @Column(name = "transfert_dispo", nullable = true)
    private Integer transfertDispo;

    @Column(name = "client_nom")
    private String clientNom;

    @Column(name = "produit_nom")
    private String produitNom;

    @Column(name = "emetteur_nom")
    private String emetteurNom;

/*    @Column(name = "entrepot_nom")
    private String entrepotNom;*/

    @Column(name = "operation_date")
    private LocalDateTime entreposageDate;


    /*@Column(name = "est_livrable")
    private Boolean estLivrable;*/

    /*@Column(name = "quantite_restante")
    private int quantiteRestante;

    @Column(name = "quantite_verse")
    private int quantiteVerse;*/


    public Ventreposage() {
        super();
    }

    public Ventreposage(Long entreposageId, String entreposageReference,int operationQte , /*String projetNom,*/ Integer transfertDispo, String clientNom, String produitNom, String emetteurNom, /*String entrepotNom,*/ LocalDateTime entreposageDate) {
        this.entreposageId = entreposageId;
        this.entreposageReference = entreposageReference;
        this.operationQte = operationQte;
       /* this.projetNom = projetNom;*/
        this.transfertDispo = transfertDispo;
        this.clientNom = clientNom;
        this.produitNom = produitNom;
        this.emetteurNom = emetteurNom;
        /*this.entrepotNom = entrepotNom;*/
        this.entreposageDate = entreposageDate;
    }

    public Long getEntreposageId() {
        return entreposageId;
    }

    public void setEntreposageId(Long entreposageId) {
        this.entreposageId = entreposageId;
    }

    public String getEntreposageReference() {
        return entreposageReference;
    }

    public void setEntreposageReference(String entreposageReference) {
        this.entreposageReference = entreposageReference;
    }

    public int getOperationQte() {
        return operationQte;
    }

    public void setOperationQte(int operationQte) {
        this.operationQte = operationQte;
    }

    /* public String getProjetNom() {
        return projetNom;
    }

    public void setProjetNom(String projetNom) {
        this.projetNom = projetNom;
    }*/

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

   /* public String getEntrepotNom() {
        return entrepotNom;
    }

    public void setEntrepotNom(String entrepotNom) {
        this.entrepotNom = entrepotNom;
    }*/

    public LocalDateTime getEntreposageDate() {
        return entreposageDate;
    }

    public void setEntreposageDate(LocalDateTime entreposageDate) {
        this.entreposageDate = entreposageDate;
    }

    public Integer getTransfertDispo() {
        return transfertDispo;
    }

    public void setTransfertDispo(Integer transfertDispo) {
        this.transfertDispo = transfertDispo;
    }
}
