package ics.ci.stock.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "motifs")
public class Motif implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long motifId;

    @Column(name = "motifs", unique = true)
    private String motifNom;

    public Motif() {
        super();
    }

    public Motif(String motifNom) {
        this.motifNom = motifNom;
    }

    public Long getMotifId() {
        return motifId;
    }

    public void setMotifId(Long motifId) {
        this.motifId = motifId;
    }

    public String getMotifNom() {
        return motifNom;
    }

    public void setMotifNom(String motifNom) {
        this.motifNom = motifNom;
    }
}
