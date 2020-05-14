package ics.ci.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_sum_stock_by_produit")
public class VsumStockByProduit {

    @Id
    @Column(name = "produit_id")
    private Long produitId;
    @Column(name = "produit_nom")
    private String produitNom;
    @Column(name = "quantite")
    private Integer quantite;
    @Column(name = "seuil_produit")
    private Integer seuilProduit;

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public void setProduitNom(String produitNom) {
        this.produitNom = produitNom;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getSeuilProduit() {
        return seuilProduit;
    }

    public void setSeuilProduit(Integer seuilProduit) {
        this.seuilProduit = seuilProduit;
    }
}
