package ics.ci.stock.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @Column(name = "stock_quantite")
    private int stockQuantite;

    @Column(name = "stock_date")
    private LocalDateTime stockDate;

    @ManyToOne
    @JoinColumn(name="projetId")
    private Projet projet;


    /*@ManyToOne
    @JoinColumn(name = "produitId")
    private Produit produit;*/

    @ManyToOne
    @JoinColumn(name = "entrepotId")
    private Entrepot entrepot;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    public Stock() {
        super();
    }

    public Stock(int stockQuantite, LocalDateTime stockDate, Projet projet, /*Produit produit,*/ Entrepot entrepot, AppUser user) {
        this.stockQuantite = stockQuantite;
        this.stockDate = stockDate;
        this.projet = projet;
      /*  this.produit = produit;*/
        this.entrepot = entrepot;
        this.user = user;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getStockQuantite() {
        return stockQuantite;
    }

    public void setStockQuantite(int stockQuantite) {
        this.stockQuantite = stockQuantite;
    }

    public LocalDateTime getStockDate() {
        return stockDate;
    }

    public void setStockDate(LocalDateTime stockDate) {
        this.stockDate = stockDate;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    /*public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }*/

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
