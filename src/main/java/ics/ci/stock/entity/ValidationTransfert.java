package ics.ci.stock.entity;

import ics.ci.stock.enums.Statut;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "validation_transfert")
public class ValidationTransfert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference", length = 10, unique = true)
    private String reference;

    @Column(name = "transfert_qte", nullable = false)
    private int transfertQte;

    private Date dateSaisie;


/*    @Column(name = "stock_initial_source",  nullable = true)
    private int stockInitialSource;

    @Column(name = "stock_final_source",  nullable = true)
    private int stockFinalSource;

    @Column(name = "stock_initial_Destination",  nullable = true)
    private int stockInitialDestination;

    @Column(name = "stock_final_destination",  nullable = true)
    private int stockFinalDestination;*/

    @ManyToOne
    @JoinColumn(name = "entreposer_id")
    private Entreposer entreposer;

    @ManyToOne
    @JoinColumn(name = "entrepotIdSource")
    private Entrepot entrepotSource;

    @ManyToOne
    @JoinColumn(name = "entrepotIdDestination")
    private Entrepot entrepotDestination;

    @Column(name = "statut")
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "projetId")
    private Projet projet;


    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;


    public ValidationTransfert() {
        super();
    }

    public ValidationTransfert(String reference,int transfertQte, Date dateSaisie /*int stockInitialSource, int stockFinalSource, int stockInitialDestination, int stockFinalDestination,*/, Entreposer entreposer, Entrepot entrepotSource, Entrepot entrepotDestination, Statut statut, Projet projet, AppUser user) {
       this.reference = reference;
        this.transfertQte = transfertQte;
        this.dateSaisie = dateSaisie;
       /* this.stockInitialSource = stockInitialSource;
        this.stockFinalSource = stockFinalSource;
        this.stockInitialDestination = stockInitialDestination;
        this.stockFinalDestination = stockFinalDestination;*/
        this.entreposer = entreposer;
        this.entrepotSource = entrepotSource;
        this.entrepotDestination = entrepotDestination;
        this.statut = statut;
        this.projet = projet;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTransfertQte() {
        return transfertQte;
    }

    public void setTransfertQte(int transfertQte) {
        this.transfertQte = transfertQte;
    }

/*    public int getStockInitialSource() {
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
    }*/

    public Entreposer getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Entreposer entreposer) {
        this.entreposer = entreposer;
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

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Date getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(Date dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
