package ics.ci.stock.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "projets" , uniqueConstraints=@UniqueConstraint(columnNames="projet_nom"))

public class Projet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projet_id;

    @Column(name = "projet_nom", unique = true)
    private String projetNom;

    @Column(name = "seuil_projet", nullable = true)
    private Integer seuilProjet;




    @ManyToOne
    @JoinColumn(name = "emetteurId")
    private Emetteur emetteur;

    @ManyToOne
    @JoinColumn(name = "produitId")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public Projet() {
        super();
    }

    public Projet(String projetNom) {
        this.projetNom = projetNom;
    }

    public Projet(String projetNom, Client client) {
        this.projetNom = projetNom;
        this.client = client;
    }

    public Projet(String projetNom, Integer seuilProjet) {
        this.projetNom = projetNom;
        this.seuilProjet = seuilProjet;
    }

    public Projet(String projetNom, Integer seuilProjet, Client client, Emetteur emetteur, Produit produit) {
        this.projetNom = projetNom;
        this.seuilProjet = seuilProjet;
        this.client = client;
        this.emetteur = emetteur;
        this.produit = produit;
    }



    public Long getProjet_id() {
        return projet_id;
    }

    public void setProjet_id(Long projet_id) {
        this.projet_id = projet_id;
    }

    public String getProjetNom() {
        return projetNom;
    }

    public void setProjetNom(String projetNom) {
        this.projetNom = projetNom;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getSeuilProjet() {
        return seuilProjet;
    }

    public void setSeuilProjet(Integer seuilProjet) {
        this.seuilProjet = seuilProjet;
    }

    public Emetteur getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Emetteur emetteur) {
        this.emetteur = emetteur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Projet)) return false;
        Projet projet = (Projet) o;
        return Objects.equals(projet_id, projet.projet_id) && Objects.equals(projetNom, projet.projetNom) && Objects.equals(seuilProjet, projet.seuilProjet) && Objects.equals(emetteur, projet.emetteur) && Objects.equals(produit, projet.produit) && Objects.equals(client, projet.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projet_id, projetNom, seuilProjet, emetteur, produit, client);
    }
}
