package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("des")
public class Destruction extends Operation {

    @Column(name = "enlevement_disponibilite", nullable = true)
    private Integer enlevementDispo;

    @Column(name = "stock_initial")
    private int stockInitial;

    @Column(name = "stock_final")
    private int stockFinal;

    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;

    @ManyToOne
    @JoinColumn(name = "entreposer_id")
    private Entreposer entreposer;

    @ManyToOne
    @JoinColumn(name = "entrepotId")
    private Entrepot entrepot;

    public Destruction() {
        super();
    }

    public Destruction(String operation_ref, int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, AppUser user, Date operationDateSaisie, Integer enlevementDispo, int stockInitial, int stockFinal, Ressource ressource, Entreposer entreposer, Entrepot entrepot) {
        super(operation_ref, operation_qte, operation_date, dispo_operation, projet, user, operationDateSaisie);
        this.enlevementDispo = enlevementDispo;
        this.stockInitial = stockInitial;
        this.stockFinal = stockFinal;
        this.ressource = ressource;
        this.entreposer = entreposer;
        this.entrepot = entrepot;
    }

    public Integer getEnlevementDispo() {
        return enlevementDispo;
    }

    public void setEnlevementDispo(Integer enlevementDispo) {
        this.enlevementDispo = enlevementDispo;
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

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    public Entreposer getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Entreposer entreposer) {
        this.entreposer = entreposer;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
}
