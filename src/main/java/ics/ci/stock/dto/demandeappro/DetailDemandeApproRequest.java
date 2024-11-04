package ics.ci.stock.dto.demandeappro;

public class DetailDemandeApproRequest {

    private int qte;
    private Long projet;

    public DetailDemandeApproRequest() {
        super();
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Long getProjet() {
        return projet;
    }

    public void setProjet(Long projet) {
        this.projet = projet;
    }
}
