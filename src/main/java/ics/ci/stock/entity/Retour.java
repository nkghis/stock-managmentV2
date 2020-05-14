package ics.ci.stock.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "retours")
public class Retour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long retourId;

    @Column(name = "retour_qte")
    private Integer retourQte;

    @Column(name = "retour_date")
    private LocalDateTime retourDate;

    @ManyToOne
    @JoinColumn(name = "operationId")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    public Retour() {
        super();
    }

    public Long getRetourId() {
        return retourId;
    }

    public void setRetourId(Long retourId) {
        this.retourId = retourId;
    }

    public Integer getRetourQte() {
        return retourQte;
    }

    public void setRetourQte(Integer retourQte) {
        this.retourQte = retourQte;
    }

    public LocalDateTime getRetourDate() {
        return retourDate;
    }

    public void setRetourDate(LocalDateTime retourDate) {
        this.retourDate = retourDate;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
