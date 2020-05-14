package ics.ci.stock.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ressources")
public class Ressource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ressource_id;

    @NotEmpty
    private String ressource_nom;

    @NotEmpty
    private String ressource_prenoms;

    @OneToMany(mappedBy = "ressource")
    private Set<Reception> receptions;

    public Ressource() {
        super();
    }

    public Ressource(@NotEmpty String ressource_nom, @NotEmpty String ressource_prenoms) {
        this.ressource_nom = ressource_nom;
        this.ressource_prenoms = ressource_prenoms;
    }

    public Long getRessource_id() {
        return ressource_id;
    }

    public void setRessource_id(Long ressource_id) {
        this.ressource_id = ressource_id;
    }

    public String getRessource_nom() {
        return ressource_nom;
    }

    public void setRessource_nom(String ressource_nom) {
        this.ressource_nom = ressource_nom;
    }

    public String getRessource_prenoms() {
        return ressource_prenoms;
    }

    public void setRessource_prenoms(String ressource_prenoms) {
        this.ressource_prenoms = ressource_prenoms;
    }

    public Set<Reception> getReceptions() {
        return receptions;
    }

    public void setReceptions(Set<Reception> receptions) {
        this.receptions = receptions;
    }
}
