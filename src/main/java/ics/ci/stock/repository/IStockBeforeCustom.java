package ics.ci.stock.repository;


public interface IStockBeforeCustom {

    Long getProjet_id();
    String getProjet();
    int getEntreposage();
    int getEnlevement();
    int getRetour();
    int getGache();
    int getStock();
    String getEntrepot();
}

