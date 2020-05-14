package ics.ci.stock.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "emetteurs")
public class Emetteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emetteur_id;

    @Column(name = "emetteur_nom")
    @NotNull
    private String emetteurNom;

    @OneToMany(mappedBy = "emetteur", fetch = FetchType.LAZY)
    private Collection<Projet> projets;

    public Emetteur() {
        super();
    }

    public Emetteur(@NotNull String emetteurNom) {
        this.emetteurNom = emetteurNom;
    }

    public Emetteur(@NotNull String emetteurNom, Collection<Projet> projets) {
        this.emetteurNom = emetteurNom;
        this.projets = projets;
    }

    public Long getEmetteur_id() {
        return emetteur_id;
    }

    public void setEmetteur_id(Long emetteur_id) {
        this.emetteur_id = emetteur_id;
    }

    public String getEmetteurNom() {
        return emetteurNom;
    }

    public void setEmetteurNom(String emetteurNom) {
        this.emetteurNom = emetteurNom;
    }

    public Collection<Projet> getProjets() {
        return projets;
    }

    public void setProjets(Collection<Projet> projets) {
        this.projets = projets;
    }
}
