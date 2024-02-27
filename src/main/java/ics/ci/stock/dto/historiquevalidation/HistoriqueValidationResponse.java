package ics.ci.stock.dto.historiquevalidation;

import java.time.LocalDateTime;

public class HistoriqueValidationResponse {

    private Long id;
    private String reference;
    private String date;
    private String commentaires;
    private String user;
    private String statut;
    private String projet;
    private String entrepotSource;
    private String entrepotDestination;
    private int qte;

    public HistoriqueValidationResponse() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
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

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
