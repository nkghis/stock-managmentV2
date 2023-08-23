package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("inv")
public class Inventairedetail extends Operation {

    @Column(name = "stock_precedent")
    private int stockPrecedent;

    @Column(name = "stock_suivant")
    private int stockSuivant;

    @Column(name = "stock_initial")
    private int stockInitial;

    @Column(name = "stock_final")
    private int stockFinal;

    @ManyToOne
    @JoinColumn(name = "entrepotId")
    private Entrepot entrepot;

    @ManyToOne
    @JoinColumn(name = "inventaireId")
    private Inventaire inventaire;

    public Inventairedetail(){
        super();
    }

    public Inventairedetail(String operation_ref, int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, AppUser user, Date operationDateSaisie, int stockPrecedent, int stockSuivant, Inventaire inventaire) {
        super(operation_ref, operation_qte, operation_date, dispo_operation, projet, user, operationDateSaisie);
        this.stockPrecedent = stockPrecedent;
        this.stockSuivant = stockSuivant;
        this.inventaire = inventaire;
    }

    public int getStockPrecedent() {
        return stockPrecedent;
    }

    public void setStockPrecedent(int stockPrecedent) {
        this.stockPrecedent = stockPrecedent;
    }

    public int getStockSuivant() {
        return stockSuivant;
    }

    public void setStockSuivant(int stockSuivant) {
        this.stockSuivant = stockSuivant;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    public int getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(int stockInitial) {
        this.stockInitial = stockInitial;
    }

    public int getStockFinal() {
        return stockFinal;
    }

    public void setStockFinal(int stockFinal) {
        this.stockFinal = stockFinal;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
}
