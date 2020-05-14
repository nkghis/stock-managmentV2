package ics.ci.stock.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="conditionnements")
public class Conditionnement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionnement_id;

    private String conditionnement_nom;

    private int piece;

    @ManyToOne
    @JoinColumn(name = "produitId")
    private Produit produit;

    public Conditionnement() {
        super();
    }

    public Conditionnement(String conditionnement_nom, int piece, Produit produit) {
        this.conditionnement_nom = conditionnement_nom;
        this.piece = piece;
        this.produit = produit;
    }

    public Long getConditionnement_id() {
        return conditionnement_id;
    }

    public void setConditionnement_id(Long conditionnement_id) {
        this.conditionnement_id = conditionnement_id;
    }

    public String getConditionnement_nom() {
        return conditionnement_nom;
    }

    public void setConditionnement_nom(String conditionnement_nom) {
        this.conditionnement_nom = conditionnement_nom;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
