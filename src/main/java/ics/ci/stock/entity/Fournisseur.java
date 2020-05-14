package ics.ci.stock.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "fournisseurs")
public class Fournisseur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fournisseur_id;

    @NotEmpty
    private String fournisseur_nom;

    @OneToMany(mappedBy = "fournisseur")
    private Set<Reception> receptions;

    public Fournisseur() {
        super();
    }

    public Fournisseur(@NotEmpty String fournisseur_nom) {
        this.fournisseur_nom = fournisseur_nom;
    }

    public Long getFournisseur_id() {
        return fournisseur_id;
    }

    public void setFournisseur_id(Long fournisseur_id) {
        this.fournisseur_id = fournisseur_id;
    }

    public String getFournisseur_nom() {
        return fournisseur_nom;
    }

    public void setFournisseur_nom(String fournisseur_nom) {
        this.fournisseur_nom = fournisseur_nom;
    }

    public Set<Reception> getReceptions() {
        return receptions;
    }

    public void setReceptions(Set<Reception> receptions) {
        this.receptions = receptions;
    }
}
