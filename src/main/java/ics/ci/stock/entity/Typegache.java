package ics.ci.stock.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "typegaches")
public class Typegache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typegacheId;

    @Column(name = "typegache_nom", unique = true)
    private String typegacheNom;

    @OneToMany(mappedBy = "typegache", fetch = FetchType.LAZY)
    private Collection<Gache> gaches;

    public Typegache() {
        super();
    }

    public Typegache(String typegacheNom, Collection<Gache> gaches) {
        this.typegacheNom = typegacheNom;
        this.gaches = gaches;
    }

    public Typegache(String typegacheNom) {
        this.typegacheNom = typegacheNom;
    }

    public Long getTypegacheId() {
        return typegacheId;
    }

    public void setTypegacheId(Long typegacheId) {
        this.typegacheId = typegacheId;
    }

    public String getTypegacheNom() {
        return typegacheNom;
    }

    public void setTypegacheNom(String typegacheNom) {
        this.typegacheNom = typegacheNom;
    }

    public Collection<Gache> getGaches() {
        return gaches;
    }

    public void setGaches(Collection<Gache> gaches) {
        this.gaches = gaches;
    }
}
