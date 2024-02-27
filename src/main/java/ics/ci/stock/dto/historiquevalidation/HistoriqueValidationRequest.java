package ics.ci.stock.dto.historiquevalidation;

import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.ValidationTransfert;
import ics.ci.stock.enums.Statut;

public class HistoriqueValidationRequest {

    private String commentaires;

    private AppUser user;

    private ValidationTransfert validationTransfert;

    private Statut statut;

    public HistoriqueValidationRequest() {
        super();
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public ValidationTransfert getValidationTransfert() {
        return validationTransfert;
    }

    public void setValidationTransfert(ValidationTransfert validationTransfert) {
        this.validationTransfert = validationTransfert;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
