package ics.ci.stock.dto.gache;

public class GacheProjetDTO {

    private Long projetId;
    private String projet;
    private int qteGache;

    public GacheProjetDTO() {
        super();
    }

    public Long getProjetId() {
        return projetId;
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public int getQteGache() {
        return qteGache;
    }

    public void setQteGache(int qteGache) {
        this.qteGache = qteGache;
    }
}
