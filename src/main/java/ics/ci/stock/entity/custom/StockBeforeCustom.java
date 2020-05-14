package ics.ci.stock.entity.custom;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;


public class StockBeforeCustom {


    private Long projet_id;
    private String projet;
    private int entreposage;
    private int enlevement;
    private int retour;
    private int gache;
    private int stock;

    /*public StockBeforeCustom() {
        super();
    }*/

   /* public interface IStockBeforeCustom
    {
        Long getProjet_id();
        String getProjet();
        int getEntreposage();
        int getEnlevement();
        int getRetour();
        int getGache();
        int getStock();


    }*/



    public StockBeforeCustom(Long projet_id, String projet, int entreposage, int enlevement, int retour, int gache, int stock) {
        this.projet_id = projet_id;
        this.projet = projet;
        this.entreposage = entreposage;
        this.enlevement = enlevement;
        this.retour = retour;
        this.gache = gache;
        this.stock = stock;
    }

    public Long getProjet_id() {
        return projet_id;
    }

    public void setProjet_id(Long projet_id) {
        this.projet_id = projet_id;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public int getEntreposage() {
        return entreposage;
    }

    public void setEntreposage(int entreposage) {
        this.entreposage = entreposage;
    }

    public int getEnlevement() {
        return enlevement;
    }

    public void setEnlevement(int enlevement) {
        this.enlevement = enlevement;
    }

    public int getRetour() {
        return retour;
    }

    public void setRetour(int retour) {
        this.retour = retour;
    }

    public int getGache() {
        return gache;
    }

    public void setGache(int gache) {
        this.gache = gache;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockBeforeCustom that = (StockBeforeCustom) o;
        return entreposage == that.entreposage &&
                enlevement == that.enlevement &&
                retour == that.retour &&
                gache == that.gache &&
                stock == that.stock &&
                Objects.equals(projet_id, that.projet_id) &&
                Objects.equals(projet, that.projet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projet_id, projet, entreposage, enlevement, retour, gache, stock);
    }
}
