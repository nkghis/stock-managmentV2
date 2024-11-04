package ics.ci.stock.entity;

import javax.persistence.*;

@Entity
@Table(name = "detail_demamade_appro")
public class DetailDemandeAppro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "qte", nullable = false)
    private int qte;

    @ManyToOne
    @JoinColumn(name = "projetId")
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "demandeApproId")
    private DemandeAppro demandeAppro;

    public DetailDemandeAppro() {
        super();
    }

    public DetailDemandeAppro(int qte, Projet projet, DemandeAppro demandeAppro) {
        this.qte = qte;
        this.projet = projet;
        this.demandeAppro = demandeAppro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public DemandeAppro getDemandeAppro() {
        return demandeAppro;
    }

    public void setDemandeAppro(DemandeAppro demandeAppro) {
        this.demandeAppro = demandeAppro;
    }
}
