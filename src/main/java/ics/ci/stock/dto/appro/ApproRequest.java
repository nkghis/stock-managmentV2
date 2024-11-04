package ics.ci.stock.dto.appro;

public class ApproRequest {

    private Long demandeApproDetail;
    private Long entreposer;
    private int qte;

    public ApproRequest() {
        super();
    }

    public Long getDemandeApproDetail() {
        return demandeApproDetail;
    }

    public void setDemandeApproDetail(Long demandeApproDetail) {
        this.demandeApproDetail = demandeApproDetail;
    }

    public Long getEntreposer() {
        return entreposer;
    }

    public void setEntreposer(Long entreposer) {
        this.entreposer = entreposer;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
