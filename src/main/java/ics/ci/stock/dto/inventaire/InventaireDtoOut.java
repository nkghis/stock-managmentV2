package ics.ci.stock.dto.inventaire;

public class InventaireDtoOut {

    private Long inventaireId;
    private String intitule;
    private String entrepot;
    private String dateDebut;
    private String dateFin;
    private String dateInventaire;

    public InventaireDtoOut() {
        super();
    }

    public InventaireDtoOut(Long inventaireId, String intitule, String entrepot, String dateDebut, String dateFin, String dateInventaire) {
        this.inventaireId = inventaireId;
        this.intitule = intitule;
        this.entrepot = entrepot;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateInventaire = dateInventaire;
    }

    public Long getInventaireId() {
        return inventaireId;
    }

    public void setInventaireId(Long inventaireId) {
        this.inventaireId = inventaireId;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateInventaire() {
        return dateInventaire;
    }

    public void setDateInventaire(String dateInventaire) {
        this.dateInventaire = dateInventaire;
    }
}
