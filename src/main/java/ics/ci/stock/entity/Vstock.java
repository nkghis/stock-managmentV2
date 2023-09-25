package ics.ci.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_stock")
public class Vstock {

    @Id
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "client_nom")
    private String clientNom;

    @Column(name = "projet_nom")
    private String projetNom;

 /*   @Column(name = "fournisseur_nom")
    private String fournisseurNom;*/


    @Column(name = "emetteur_nom")
    private String emetteurNom;

    @Column(name = "entrepot_nom")
    private String entrepotNom;

    @Column(name = "produit_nom")
    private String produitNom;

    @Column(name = "provider_nom")
    private String fournisseurNom;

    @Column(name = "stock_quantite")
    private  Integer stockQuantite;

    @Column(name = "seuil_projet")
    private  Integer seuilProjet;

    public Vstock() {
        super();
    }

    public String getFournisseurNom() {
        return fournisseurNom;
    }

    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getProjetNom() {
        return projetNom;
    }

    public void setProjetNom(String projetNom) {
        this.projetNom = projetNom;
    }

    public String getEntrepotNom() {
        return entrepotNom;
    }

    public void setEntrepotNom(String entrepotNom) {
        this.entrepotNom = entrepotNom;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public void setProduitNom(String produitNom) {
        this.produitNom = produitNom;
    }

    public Integer getStockQuantite() {
        return stockQuantite;
    }

    public void setStockQuantite(Integer stockQuantite) {
        this.stockQuantite = stockQuantite;
    }

    public Integer getSeuilProjet() {
        return seuilProjet;
    }

    public void setSeuilProjet(Integer seuilProjet) {
        this.seuilProjet = seuilProjet;
    }

    public String getEmetteurNom() {
        return emetteurNom;
    }

    public void setEmetteurNom(String emetteurNom) {
        this.emetteurNom = emetteurNom;
    }
}
