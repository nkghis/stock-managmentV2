package ics.ci.stock.dto.inventaire;

import ics.ci.stock.entity.Entrepot;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class InventaireNewDtos {


    private String intitule;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    private Long entrepot;

    public InventaireNewDtos() {
        super();
    }

    public InventaireNewDtos(String intitule, Date dateDebut, Date dateFin, Long entrepot) {
        this.intitule = intitule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.entrepot = entrepot;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Long getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Long entrepot) {
        this.entrepot = entrepot;
    }
}
