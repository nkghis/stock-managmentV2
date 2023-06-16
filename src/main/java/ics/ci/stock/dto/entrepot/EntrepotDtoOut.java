package ics.ci.stock.dto.entrepot;

public class EntrepotDtoOut {

    private Long entrepotId;
    private String entrepotNom;

    public EntrepotDtoOut() {
        super();
    }

    public EntrepotDtoOut(Long entrepotId, String entrepotNom) {
        this.entrepotId = entrepotId;
        this.entrepotNom = entrepotNom;
    }

    public Long getEntrepotId() {
        return entrepotId;
    }

    public void setEntrepotId(Long entrepotId) {
        this.entrepotId = entrepotId;
    }

    public String getEntrepotNom() {
        return entrepotNom;
    }

    public void setEntrepotNom(String entrepotNom) {
        this.entrepotNom = entrepotNom;
    }
}
