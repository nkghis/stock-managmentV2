package ics.ci.stock.dto.inventaire;

import com.fasterxml.jackson.annotation.JsonFormat;
import ics.ci.stock.entity.Entrepot;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;


public class InventaireNewDto {

    @NotNull
    //@Size(min = 3 , max = 10)
    private String intitule;
    @NotNull
    private Entrepot entrepot;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   // @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDebut;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateFin;



    public InventaireNewDto() {
        super();
    }

    public InventaireNewDto(String intitule, Entrepot entrepot, Date dateDebut, Date dateFin) {
        this.intitule = intitule;
        this.entrepot = entrepot;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
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
}
