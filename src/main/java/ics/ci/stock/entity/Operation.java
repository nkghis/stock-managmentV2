package ics.ci.stock.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "operations"/*, //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_OPERATION_UK", columnNames = "operationReference") }*/)

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeoperation")
public abstract class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;

    @Column(name = "operationReference", length = 36, unique = true)
    private String operationReference;

    /*@Column(name = "reference_fournisseur", length = 36, nullable = false)
    private String operationReferenceFournisseur;*/

    @Column(name = "operation_qte", nullable = false)
    private int operationQte;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operation_date;

    @Column(name = "dispo_operation", nullable = true)
    private boolean estDisponible;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "operation_date_saisie", nullable = true)
    private Date operationDateSaisie;


    @ManyToOne
    @JoinColumn(name = "projetId")
    private Projet projet;

    /*@ManyToOne
    @JoinColumn(name = "produitId")
    private Produit produit;*/


    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    @OneToMany(mappedBy = "operation", fetch = FetchType.LAZY)
    private Collection<Gache> gaches;

    @OneToMany(mappedBy = "operation", fetch = FetchType.LAZY)
    private Collection<Retour> retours;



    public Operation() {
        super();
    }

    public Operation(String operation_ref,/* String operation_ref_fournisseur,*/ int operation_qte, LocalDateTime operation_date, boolean dispo_operation, Projet projet, /*Produit produit,*/ AppUser user, Date operationDateSaisie) {
        this.operationReference = operation_ref;
        //this.operationReferenceFournisseur = operation_ref_fournisseur;
        this.operationQte = operation_qte;
        this.operation_date = operation_date;
        this.estDisponible = dispo_operation;
        this.projet = projet;
      /*  this.produit = produit;*/
        this.user = user;
        this.operationDateSaisie = operationDateSaisie;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getOperationReference() {
        return operationReference;
    }

    public void setOperationReference(String operationReference) {
        this.operationReference = operationReference;
    }

    public int getOperation_qte() {
        return operationQte;
    }

    public void setOperation_qte(int operation_qte) {
        this.operationQte = operation_qte;
    }

    public LocalDateTime getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(LocalDateTime operation_date) {
        this.operation_date = operation_date;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
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

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public int getOperationQte() {
        return operationQte;
    }

    public void setOperationQte(int operationQte) {
        this.operationQte = operationQte;
    }

    public Collection<Gache> getGaches() {
        return gaches;
    }

    public void setGaches(Collection<Gache> gaches) {
        this.gaches = gaches;
    }

    public Collection<Retour> getRetours() {
        return retours;
    }

    public void setRetours(Collection<Retour> retours) {
        this.retours = retours;
    }

    public Date getOperationDateSaisie() {
        return operationDateSaisie;
    }

    public void setOperationDateSaisie(Date operationDateSaisie) {
        this.operationDateSaisie = operationDateSaisie;
    }
}
