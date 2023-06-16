package ics.ci.stock.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "produits")
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produit_id;
    private String produit_nom;

    /*@Column(name = "seuil_produit", nullable = true)
    private Integer seuilProduit;*/

    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    private Collection<Projet> projets;


/*
    @OneToMany(mappedBy = "produit")
    private Collection<Conditionnement> conditionnements;*/

    public Produit() {
        super();
    }



    public Produit(String produit_nom) {
        this.produit_nom = produit_nom;
    }



    public Produit(String produit_nom, Collection<Projet> projets) {
        this.produit_nom = produit_nom;
        this.projets = projets;

    }


    public Long getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(Long produit_id) {
        this.produit_id = produit_id;
    }

    public String getProduit_nom() {
        return produit_nom;
    }

    public void setProduit_nom(String produit_nom) {
        this.produit_nom = produit_nom;
    }

/*    public Collection<Conditionnement> getConditionnements() {
        return conditionnements;
    }

    public void setConditionnements(Collection<Conditionnement> conditionnements) {
        this.conditionnements = conditionnements;
    }*/

    public Collection<Projet> getProjets() {
        return projets;
    }

    public void setProjets(Collection<Projet> projets) {
        this.projets = projets;
    }
}
