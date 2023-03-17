package ics.ci.stock.entity.custom;

import java.time.LocalDateTime;
import java.util.Date;

public class DestructionDto {

    private Long operationId;
    private String operationReference;
    private int operationQte;
    private String  operationDate;
    private String operationDateSaisie;
    private String projet;
    private String user;
    private Integer enlevementDispo;
    private int stockInitial;
    private int stockFinal;
    private String ressource;
    private Long entreposer;
    private String entrepot;

    public DestructionDto() {
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

    public int getOperationQte() {
        return operationQte;
    }

    public void setOperationQte(int operationQte) {
        this.operationQte = operationQte;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationDateSaisie() {
        return operationDateSaisie;
    }

    public void setOperationDateSaisie(String operationDateSaisie) {
        this.operationDateSaisie = operationDateSaisie;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public Long getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Long entreposer) {
        this.entreposer = entreposer;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }
}
