package ics.ci.stock.entity.custom;


import javax.persistence.Id;

public class GacheProjetDto {

    @Id
    private Long id;

    private String projet;

    private Integer qte;

    public GacheProjetDto() {
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

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }
}
