package ics.ci.stock.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("tra")
public class Transfert extends Operation {


    @Column(name = "stock_initial_source",  nullable = true)
    private int stockInitialSource;

    @Column(name = "stock_final_source",  nullable = true)
    private int stockFinalSource;

    @Column(name = "stock_initial_Destination",  nullable = true)
    private int stockInitialDestination;

    @Column(name = "stock_final_destination",  nullable = true)
    private int stockFinalDestination;

    @ManyToOne
    @JoinColumn(name = "entreposer_id")
    private Entreposer entreposer;

    @ManyToOne
    @JoinColumn(name = "entrepotIdSource")
    private Entrepot entrepotSource;

    @ManyToOne
    @JoinColumn(name = "entrepotIdDestination")
    private Entrepot entrepotDestination;

    @ManyToOne
    @JoinColumn(name="validationId")
    private ValidationTransfert validationTransfert;

    public Transfert(){
        super();
    }

    public Transfert(String operation_ref, int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, AppUser user, Date operationDateSaisie, int stockInitialSource, int stockFinalSource, int stockInitialDestination, int stockFinalDestination, Entrepot entrepotSource, Entrepot entrepotDestination, Entreposer entreposer) {
        super(operation_ref, operation_qte, operation_date, dispo_operation, projet, user, operationDateSaisie);
        this.stockInitialSource = stockInitialSource;
        this.stockFinalSource = stockFinalSource;
        this.stockInitialDestination = stockInitialDestination;
        this.stockFinalDestination = stockFinalDestination;
        this.entrepotSource = entrepotSource;
        this.entrepotDestination = entrepotDestination;
        this.entreposer = entreposer;
    }

    public int getStockInitialSource() {
        return stockInitialSource;
    }

    public void setStockInitialSource(int stockInitialSource) {
        this.stockInitialSource = stockInitialSource;
    }

    public int getStockFinalSource() {
        return stockFinalSource;
    }

    public void setStockFinalSource(int stockFinalSource) {
        this.stockFinalSource = stockFinalSource;
    }

    public int getStockInitialDestination() {
        return stockInitialDestination;
    }

    public void setStockInitialDestination(int stockInitialDestination) {
        this.stockInitialDestination = stockInitialDestination;
    }

    public int getStockFinalDestination() {
        return stockFinalDestination;
    }

    public void setStockFinalDestination(int stockFinalDestination) {
        this.stockFinalDestination = stockFinalDestination;
    }

    public Entrepot getEntrepotSource() {
        return entrepotSource;
    }

    public void setEntrepotSource(Entrepot entrepotSource) {
        this.entrepotSource = entrepotSource;
    }

    public Entrepot getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(Entrepot entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

    public Entreposer getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Entreposer entreposer) {
        this.entreposer = entreposer;
    }
}
