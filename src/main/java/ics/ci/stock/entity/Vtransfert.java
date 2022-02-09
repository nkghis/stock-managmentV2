package ics.ci.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/*@Entity
@Table(name = "v_transfert")*/
public class Vtransfert {

    @Id
    @Column(name = "operation_id")
    private Long operationId;

    @Column(name = "operation_reference")
    private String operationReference;

    @Column(name = "projet_nom")
    private String projetNom;

    @Column(name = "operation_qte")
    private int qteOperation;

   /* @Column(name = "disponibilite")
    private int disponibilite;*/

    /*@Column(name = "typeoperation")
    private String typeOperation;*/

/*    @Column(name = "dispo_operation")
    private boolean dispoOperation;*/



    @Column(name = "initial_source")
    private int initialSource;

    @Column(name = "final_source")
    private int finalSource;

    @Column(name = "initial_destination")
    private int initialDestination;

    @Column(name = "final_destination")
    private int finalDestination;


    @Column(name = "entrepot_source")
    private String entrepotSource;

    @Column(name = "entrepot_destination")
    private String entrepotDestination;

    @Column(name = "user_nom")
    private String userName;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;



    public Vtransfert() {
        super();
    }

    public Vtransfert(Long operationId, String operationReference, String projetNom, int qteOperation, int initialSource, int finalSource, int initialDestination, int finalDestination, String entrepotSource, String entrepotDestination, String userName, LocalDateTime operationDate) {
        this.operationId = operationId;
        this.operationReference = operationReference;
        this.projetNom = projetNom;
        this.qteOperation = qteOperation;
        this.initialSource = initialSource;
        this.finalSource = finalSource;
        this.initialDestination = initialDestination;
        this.finalDestination = finalDestination;
        this.entrepotSource = entrepotSource;
        this.entrepotDestination = entrepotDestination;
        this.userName = userName;
        this.operationDate = operationDate;
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

    public String getProjetNom() {
        return projetNom;
    }

    public void setProjetNom(String projetNom) {
        this.projetNom = projetNom;
    }

    public int getQteOperation() {
        return qteOperation;
    }

    public void setQteOperation(int qteOperation) {
        this.qteOperation = qteOperation;
    }

    public int getInitialSource() {
        return initialSource;
    }

    public void setInitialSource(int initialSource) {
        this.initialSource = initialSource;
    }

    public int getFinalSource() {
        return finalSource;
    }

    public void setFinalSource(int finalSource) {
        this.finalSource = finalSource;
    }

    public int getInitialDestination() {
        return initialDestination;
    }

    public void setInitialDestination(int initialDestination) {
        this.initialDestination = initialDestination;
    }

    public int getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(int finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getEntrepotSource() {
        return entrepotSource;
    }

    public void setEntrepotSource(String entrepotSource) {
        this.entrepotSource = entrepotSource;
    }

    public String getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(String entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }
}

