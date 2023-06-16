package ics.ci.stock.dto.projet;

public class ProjetDtoOut {

    private Long projetId;
    private String projetName;

    public ProjetDtoOut() {
        super();
    }

    public ProjetDtoOut(Long projetId, String projetName) {
        this.projetId = projetId;
        this.projetName = projetName;
    }

    public Long getProjetId() {
        return projetId;
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    public String getProjetName() {
        return projetName;
    }

    public void setProjetName(String projetName) {
        this.projetName = projetName;
    }
}
