package ics.ci.stock.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_gache_mouvement")
public class Vgachemouvement {

    @Id
    private Long id;
    private String projet;
    private String typedegache;
    private Integer qte;
    private String datesaisie;
    private String dategache;

    public Vgachemouvement() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getTypedegache() {
        return typedegache;
    }

    public void setTypedegache(String typedegache) {
        this.typedegache = typedegache;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public String getDatesaisie() {
        return datesaisie;
    }

    public void setDatesaisie(String datesaisie) {
        this.datesaisie = datesaisie;
    }

    public String getDategache() {
        return dategache;
    }

    public void setDategache(String dategache) {
        this.dategache = dategache;
    }
}
