package ics.ci.stock.dto.demandeappro;

public class DetailDemandeApproResponse {

    private int qte;
    private String projet;

    public DetailDemandeApproResponse() {
        super();
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }
}
