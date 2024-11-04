package ics.ci.stock.dto.demandeappro;

import java.util.List;

public class DemandeApproResponse {

    private String reference;

    private String statut;

    private String entrepotSource;

    private String date;

    private String utilisateur;

    private List<DetailDemandeApproResponse> details;

    public DemandeApproResponse() {
        super();
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getEntrepotSource() {
        return entrepotSource;
    }

    public void setEntrepotSource(String entrepotSource) {
        this.entrepotSource = entrepotSource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<DetailDemandeApproResponse> getDetails() {
        return details;
    }

    public void setDetails(List<DetailDemandeApproResponse> details) {
        this.details = details;
    }
}
