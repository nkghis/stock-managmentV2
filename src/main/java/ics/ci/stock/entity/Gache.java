package ics.ci.stock.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "gaches")
public class Gache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gacheId;

    @Column(name = "gache_qte")
    private Integer gacheQte;

    @Column(name = "gache_date")
    private LocalDateTime gacheDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "operation_date_saisie", nullable = true)
    private Date operationDateSaisie;


    @ManyToOne
    @JoinColumn(name = "operationId")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "appuserId")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "typegacheId")
    private Typegache typegache;

    public Gache() {
        super();
    }

    public Gache(Integer gacheQte, LocalDateTime gacheDate,Date operationDateSaisie , Operation operation, AppUser user, Typegache typegache) {
        this.gacheQte = gacheQte;
        this.gacheDate = gacheDate;
        this.operationDateSaisie = operationDateSaisie;
        this.operation = operation;
        this.user = user;
        this.typegache = typegache;
    }

    public Long getGacheId() {
        return gacheId;
    }

    public void setGacheId(Long gacheId) {
        this.gacheId = gacheId;
    }

    public Integer getGacheQte() {
        return gacheQte;
    }

    public void setGacheQte(Integer gacheQte) {
        this.gacheQte = gacheQte;
    }

    public LocalDateTime getGacheDate() {
        return gacheDate;
    }

    public void setGacheDate(LocalDateTime gacheDate) {
        this.gacheDate = gacheDate;
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

    public Typegache getTypegache() {
        return typegache;
    }

    public void setTypegache(Typegache typegache) {
        this.typegache = typegache;
    }

    public Date getOperationDateSaisie() {
        return operationDateSaisie;
    }

    public void setOperationDateSaisie(Date operationDateSaisie) {
        this.operationDateSaisie = operationDateSaisie;
    }

}
