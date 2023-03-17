package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("dis")
public class Entreposer extends Operation {

   /* @Column(name = "entreposage_reference", length = 36, unique = true)
    private String entreposageReference;*/

    @Column(name = "quantite_verse")
    private int qteVerse;

    @Column(name = "quantite_restante")
    private int qteRestante;

    @Column(name = "est_livrable")
    private Boolean estLivrable;


    @Column(name = "stock_initial")
    private int stockInitial;

    @Column(name = "stock_final")
    private int stockFinal;

    //Add for manage transfert
    @Column(name = "transfert_dispo", nullable = true)
    private Integer transfertDispo;

    @ManyToOne
    @JoinColumn(name = "receptionId")
    private Reception reception;

    @ManyToOne
    @JoinColumn(name = "entrepotId")
    private Entrepot entrepot;

    /*@ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;*/


    public Entreposer() {
        super();
    }

    public Entreposer(String operation_ref, int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, AppUser user, Date operationDateSaisie, int qteVerse, int qteRestante, Boolean estLivrable, int stockInitial, int stockFinal, Integer transfertDispo, Reception reception, Entrepot entrepot) {
        super(operation_ref, operation_qte, operation_date, dispo_operation, projet, user, operationDateSaisie);
        this.qteVerse = qteVerse;
        this.qteRestante = qteRestante;
        this.estLivrable = estLivrable;
        this.stockInitial = stockInitial;
        this.stockFinal = stockFinal;
        //Add for manage transfert
        this.transfertDispo = transfertDispo;
        this.reception = reception;
        this.entrepot = entrepot;
    }

    public int getQteVerse() {
        return qteVerse;
    }

    public void setQteVerse(int qteVerse) {
        this.qteVerse = qteVerse;
    }

    public int getQteRestante() {
        return qteRestante;
    }

    public void setQteRestante(int qteRestante) {
        this.qteRestante = qteRestante;
    }

    public Boolean getEstLivrable() {
        return estLivrable;
    }

    public void setEstLivrable(Boolean estLivrable) {
        this.estLivrable = estLivrable;
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

    //Add for manage transfert


    public Integer getTransfertDispo() {
        return transfertDispo;
    }

    public void setTransfertDispo(Integer transfertDispo) {
        this.transfertDispo = transfertDispo;
    }

    public Reception getReception() {
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }
}
