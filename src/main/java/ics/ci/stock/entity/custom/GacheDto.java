package ics.ci.stock.entity.custom;

import java.util.Date;

public class GacheDto {

    private String projet;
    private String typedegache;
    private Integer qte;
    private Date datesaisie;
    private Date dategache;


    public GacheDto() {
        super();
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public String getTypedegache() {
        return typedegache;
    }

    public void setTypedegache(String typedegache) {
        this.typedegache = typedegache;
    }

    public Date getDatesaisie() {
        return datesaisie;
    }

    public void setDatesaisie(Date datesaisie) {
        this.datesaisie = datesaisie;
    }

    public Date getDategache() {
        return dategache;
    }

    public void setDategache(Date dategache) {
        this.dategache = dategache;
    }
}
